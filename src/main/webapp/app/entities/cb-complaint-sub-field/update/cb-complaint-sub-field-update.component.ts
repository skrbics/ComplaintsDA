import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbComplaintSubField } from '../cb-complaint-sub-field.model';
import { CbComplaintSubFieldService } from '../service/cb-complaint-sub-field.service';
import { CbComplaintSubFieldFormService, CbComplaintSubFieldFormGroup } from './cb-complaint-sub-field-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cb-complaint-sub-field-update',
  templateUrl: './cb-complaint-sub-field-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CbComplaintSubFieldUpdateComponent implements OnInit {
  isSaving = false;
  cbComplaintSubField: ICbComplaintSubField | null = null;

  protected cbComplaintSubFieldService = inject(CbComplaintSubFieldService);
  protected cbComplaintSubFieldFormService = inject(CbComplaintSubFieldFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CbComplaintSubFieldFormGroup = this.cbComplaintSubFieldFormService.createCbComplaintSubFieldFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbComplaintSubField }) => {
      this.cbComplaintSubField = cbComplaintSubField;
      if (cbComplaintSubField) {
        this.updateForm(cbComplaintSubField);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbComplaintSubField = this.cbComplaintSubFieldFormService.getCbComplaintSubField(this.editForm);
    if (cbComplaintSubField.id !== null) {
      this.subscribeToSaveResponse(this.cbComplaintSubFieldService.update(cbComplaintSubField));
    } else {
      this.subscribeToSaveResponse(this.cbComplaintSubFieldService.create(cbComplaintSubField));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbComplaintSubField>>): void {
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

  protected updateForm(cbComplaintSubField: ICbComplaintSubField): void {
    this.cbComplaintSubField = cbComplaintSubField;
    this.cbComplaintSubFieldFormService.resetForm(this.editForm, cbComplaintSubField);
  }
}
