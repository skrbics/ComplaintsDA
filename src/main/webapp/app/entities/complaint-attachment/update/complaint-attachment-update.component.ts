import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IComplaint } from 'app/entities/complaint/complaint.model';
import { ComplaintService } from 'app/entities/complaint/service/complaint.service';
import { ICbAttachmentType } from 'app/entities/cb-attachment-type/cb-attachment-type.model';
import { CbAttachmentTypeService } from 'app/entities/cb-attachment-type/service/cb-attachment-type.service';
import { ComplaintAttachmentService } from '../service/complaint-attachment.service';
import { IComplaintAttachment } from '../complaint-attachment.model';
import { ComplaintAttachmentFormService, ComplaintAttachmentFormGroup } from './complaint-attachment-form.service';

@Component({
  standalone: true,
  selector: 'jhi-complaint-attachment-update',
  templateUrl: './complaint-attachment-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ComplaintAttachmentUpdateComponent implements OnInit {
  isSaving = false;
  complaintAttachment: IComplaintAttachment | null = null;

  complaintsSharedCollection: IComplaint[] = [];
  cbAttachmentTypesSharedCollection: ICbAttachmentType[] = [];

  protected complaintAttachmentService = inject(ComplaintAttachmentService);
  protected complaintAttachmentFormService = inject(ComplaintAttachmentFormService);
  protected complaintService = inject(ComplaintService);
  protected cbAttachmentTypeService = inject(CbAttachmentTypeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ComplaintAttachmentFormGroup = this.complaintAttachmentFormService.createComplaintAttachmentFormGroup();

  compareComplaint = (o1: IComplaint | null, o2: IComplaint | null): boolean => this.complaintService.compareComplaint(o1, o2);

  compareCbAttachmentType = (o1: ICbAttachmentType | null, o2: ICbAttachmentType | null): boolean =>
    this.cbAttachmentTypeService.compareCbAttachmentType(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ complaintAttachment }) => {
      this.complaintAttachment = complaintAttachment;
      if (complaintAttachment) {
        this.updateForm(complaintAttachment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const complaintAttachment = this.complaintAttachmentFormService.getComplaintAttachment(this.editForm);
    if (complaintAttachment.id !== null) {
      this.subscribeToSaveResponse(this.complaintAttachmentService.update(complaintAttachment));
    } else {
      this.subscribeToSaveResponse(this.complaintAttachmentService.create(complaintAttachment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComplaintAttachment>>): void {
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

  protected updateForm(complaintAttachment: IComplaintAttachment): void {
    this.complaintAttachment = complaintAttachment;
    this.complaintAttachmentFormService.resetForm(this.editForm, complaintAttachment);

    this.complaintsSharedCollection = this.complaintService.addComplaintToCollectionIfMissing<IComplaint>(
      this.complaintsSharedCollection,
      complaintAttachment.complaint,
    );
    this.cbAttachmentTypesSharedCollection = this.cbAttachmentTypeService.addCbAttachmentTypeToCollectionIfMissing<ICbAttachmentType>(
      this.cbAttachmentTypesSharedCollection,
      complaintAttachment.cbAttachmentType,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.complaintService
      .query()
      .pipe(map((res: HttpResponse<IComplaint[]>) => res.body ?? []))
      .pipe(
        map((complaints: IComplaint[]) =>
          this.complaintService.addComplaintToCollectionIfMissing<IComplaint>(complaints, this.complaintAttachment?.complaint),
        ),
      )
      .subscribe((complaints: IComplaint[]) => (this.complaintsSharedCollection = complaints));

    this.cbAttachmentTypeService
      .query()
      .pipe(map((res: HttpResponse<ICbAttachmentType[]>) => res.body ?? []))
      .pipe(
        map((cbAttachmentTypes: ICbAttachmentType[]) =>
          this.cbAttachmentTypeService.addCbAttachmentTypeToCollectionIfMissing<ICbAttachmentType>(
            cbAttachmentTypes,
            this.complaintAttachment?.cbAttachmentType,
          ),
        ),
      )
      .subscribe((cbAttachmentTypes: ICbAttachmentType[]) => (this.cbAttachmentTypesSharedCollection = cbAttachmentTypes));
  }
}
