import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbCity } from '../cb-city.model';
import { CbCityService } from '../service/cb-city.service';
import { CbCityFormService, CbCityFormGroup } from './cb-city-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cb-city-update',
  templateUrl: './cb-city-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CbCityUpdateComponent implements OnInit {
  isSaving = false;
  cbCity: ICbCity | null = null;

  protected cbCityService = inject(CbCityService);
  protected cbCityFormService = inject(CbCityFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CbCityFormGroup = this.cbCityFormService.createCbCityFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbCity }) => {
      this.cbCity = cbCity;
      if (cbCity) {
        this.updateForm(cbCity);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbCity = this.cbCityFormService.getCbCity(this.editForm);
    if (cbCity.id !== null) {
      this.subscribeToSaveResponse(this.cbCityService.update(cbCity));
    } else {
      this.subscribeToSaveResponse(this.cbCityService.create(cbCity));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbCity>>): void {
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

  protected updateForm(cbCity: ICbCity): void {
    this.cbCity = cbCity;
    this.cbCityFormService.resetForm(this.editForm, cbCity);
  }
}
