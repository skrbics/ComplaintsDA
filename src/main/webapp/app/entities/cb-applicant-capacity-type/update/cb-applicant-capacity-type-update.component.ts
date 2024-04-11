import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbApplicantCapacityType } from '../cb-applicant-capacity-type.model';
import { CbApplicantCapacityTypeService } from '../service/cb-applicant-capacity-type.service';
import { CbApplicantCapacityTypeFormService, CbApplicantCapacityTypeFormGroup } from './cb-applicant-capacity-type-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cb-applicant-capacity-type-update',
  templateUrl: './cb-applicant-capacity-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CbApplicantCapacityTypeUpdateComponent implements OnInit {
  isSaving = false;
  cbApplicantCapacityType: ICbApplicantCapacityType | null = null;

  protected cbApplicantCapacityTypeService = inject(CbApplicantCapacityTypeService);
  protected cbApplicantCapacityTypeFormService = inject(CbApplicantCapacityTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CbApplicantCapacityTypeFormGroup = this.cbApplicantCapacityTypeFormService.createCbApplicantCapacityTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbApplicantCapacityType }) => {
      this.cbApplicantCapacityType = cbApplicantCapacityType;
      if (cbApplicantCapacityType) {
        this.updateForm(cbApplicantCapacityType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbApplicantCapacityType = this.cbApplicantCapacityTypeFormService.getCbApplicantCapacityType(this.editForm);
    if (cbApplicantCapacityType.id !== null) {
      this.subscribeToSaveResponse(this.cbApplicantCapacityTypeService.update(cbApplicantCapacityType));
    } else {
      this.subscribeToSaveResponse(this.cbApplicantCapacityTypeService.create(cbApplicantCapacityType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbApplicantCapacityType>>): void {
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

  protected updateForm(cbApplicantCapacityType: ICbApplicantCapacityType): void {
    this.cbApplicantCapacityType = cbApplicantCapacityType;
    this.cbApplicantCapacityTypeFormService.resetForm(this.editForm, cbApplicantCapacityType);
  }
}
