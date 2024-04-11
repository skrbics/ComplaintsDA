import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbCountry } from '../cb-country.model';
import { CbCountryService } from '../service/cb-country.service';
import { CbCountryFormService, CbCountryFormGroup } from './cb-country-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cb-country-update',
  templateUrl: './cb-country-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CbCountryUpdateComponent implements OnInit {
  isSaving = false;
  cbCountry: ICbCountry | null = null;

  protected cbCountryService = inject(CbCountryService);
  protected cbCountryFormService = inject(CbCountryFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CbCountryFormGroup = this.cbCountryFormService.createCbCountryFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbCountry }) => {
      this.cbCountry = cbCountry;
      if (cbCountry) {
        this.updateForm(cbCountry);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbCountry = this.cbCountryFormService.getCbCountry(this.editForm);
    if (cbCountry.id !== null) {
      this.subscribeToSaveResponse(this.cbCountryService.update(cbCountry));
    } else {
      this.subscribeToSaveResponse(this.cbCountryService.create(cbCountry));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbCountry>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(cbCountry: ICbCountry): void {
    this.cbCountry = cbCountry;
    this.cbCountryFormService.resetForm(this.editForm, cbCountry);
  }
}
