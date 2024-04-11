import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbComplaintField } from '../cb-complaint-field.model';
import { CbComplaintFieldService } from '../service/cb-complaint-field.service';
import { CbComplaintFieldFormService, CbComplaintFieldFormGroup } from './cb-complaint-field-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cb-complaint-field-update',
  templateUrl: './cb-complaint-field-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CbComplaintFieldUpdateComponent implements OnInit {
  isSaving = false;
  cbComplaintField: ICbComplaintField | null = null;

  protected cbComplaintFieldService = inject(CbComplaintFieldService);
  protected cbComplaintFieldFormService = inject(CbComplaintFieldFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CbComplaintFieldFormGroup = this.cbComplaintFieldFormService.createCbComplaintFieldFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbComplaintField }) => {
      this.cbComplaintField = cbComplaintField;
      if (cbComplaintField) {
        this.updateForm(cbComplaintField);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbComplaintField = this.cbComplaintFieldFormService.getCbComplaintField(this.editForm);
    if (cbComplaintField.id !== null) {
      this.subscribeToSaveResponse(this.cbComplaintFieldService.update(cbComplaintField));
    } else {
      this.subscribeToSaveResponse(this.cbComplaintFieldService.create(cbComplaintField));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbComplaintField>>): void {
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

  protected updateForm(cbComplaintField: ICbComplaintField): void {
    this.cbComplaintField = cbComplaintField;
    this.cbComplaintFieldFormService.resetForm(this.editForm, cbComplaintField);
  }
}
