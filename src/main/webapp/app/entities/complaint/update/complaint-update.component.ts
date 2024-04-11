import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IApplicant } from 'app/entities/applicant/applicant.model';
import { ApplicantService } from 'app/entities/applicant/service/applicant.service';
import { ICbComplaintField } from 'app/entities/cb-complaint-field/cb-complaint-field.model';
import { CbComplaintFieldService } from 'app/entities/cb-complaint-field/service/cb-complaint-field.service';
import { ICbComplaintSubField } from 'app/entities/cb-complaint-sub-field/cb-complaint-sub-field.model';
import { CbComplaintSubFieldService } from 'app/entities/cb-complaint-sub-field/service/cb-complaint-sub-field.service';
import { ICbComplaintType } from 'app/entities/cb-complaint-type/cb-complaint-type.model';
import { CbComplaintTypeService } from 'app/entities/cb-complaint-type/service/cb-complaint-type.service';
import { ICbComplaintChannel } from 'app/entities/cb-complaint-channel/cb-complaint-channel.model';
import { CbComplaintChannelService } from 'app/entities/cb-complaint-channel/service/cb-complaint-channel.service';
import { ICbApplicantCapacityType } from 'app/entities/cb-applicant-capacity-type/cb-applicant-capacity-type.model';
import { CbApplicantCapacityTypeService } from 'app/entities/cb-applicant-capacity-type/service/cb-applicant-capacity-type.service';
import { ICbComplaintPhase } from 'app/entities/cb-complaint-phase/cb-complaint-phase.model';
import { CbComplaintPhaseService } from 'app/entities/cb-complaint-phase/service/cb-complaint-phase.service';
import { ComplaintService } from '../service/complaint.service';
import { IComplaint } from '../complaint.model';
import { ComplaintFormService, ComplaintFormGroup } from './complaint-form.service';

@Component({
  standalone: true,
  selector: 'jhi-complaint-update',
  templateUrl: './complaint-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ComplaintUpdateComponent implements OnInit {
  isSaving = false;
  complaint: IComplaint | null = null;

  applicantsSharedCollection: IApplicant[] = [];
  cbComplaintFieldsSharedCollection: ICbComplaintField[] = [];
  cbComplaintSubFieldsSharedCollection: ICbComplaintSubField[] = [];
  cbComplaintTypesSharedCollection: ICbComplaintType[] = [];
  cbComplaintChannelsSharedCollection: ICbComplaintChannel[] = [];
  cbApplicantCapacityTypesSharedCollection: ICbApplicantCapacityType[] = [];
  cbComplaintPhasesSharedCollection: ICbComplaintPhase[] = [];

  protected complaintService = inject(ComplaintService);
  protected complaintFormService = inject(ComplaintFormService);
  protected applicantService = inject(ApplicantService);
  protected cbComplaintFieldService = inject(CbComplaintFieldService);
  protected cbComplaintSubFieldService = inject(CbComplaintSubFieldService);
  protected cbComplaintTypeService = inject(CbComplaintTypeService);
  protected cbComplaintChannelService = inject(CbComplaintChannelService);
  protected cbApplicantCapacityTypeService = inject(CbApplicantCapacityTypeService);
  protected cbComplaintPhaseService = inject(CbComplaintPhaseService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ComplaintFormGroup = this.complaintFormService.createComplaintFormGroup();

  compareApplicant = (o1: IApplicant | null, o2: IApplicant | null): boolean => this.applicantService.compareApplicant(o1, o2);

  compareCbComplaintField = (o1: ICbComplaintField | null, o2: ICbComplaintField | null): boolean =>
    this.cbComplaintFieldService.compareCbComplaintField(o1, o2);

  compareCbComplaintSubField = (o1: ICbComplaintSubField | null, o2: ICbComplaintSubField | null): boolean =>
    this.cbComplaintSubFieldService.compareCbComplaintSubField(o1, o2);

  compareCbComplaintType = (o1: ICbComplaintType | null, o2: ICbComplaintType | null): boolean =>
    this.cbComplaintTypeService.compareCbComplaintType(o1, o2);

  compareCbComplaintChannel = (o1: ICbComplaintChannel | null, o2: ICbComplaintChannel | null): boolean =>
    this.cbComplaintChannelService.compareCbComplaintChannel(o1, o2);

  compareCbApplicantCapacityType = (o1: ICbApplicantCapacityType | null, o2: ICbApplicantCapacityType | null): boolean =>
    this.cbApplicantCapacityTypeService.compareCbApplicantCapacityType(o1, o2);

  compareCbComplaintPhase = (o1: ICbComplaintPhase | null, o2: ICbComplaintPhase | null): boolean =>
    this.cbComplaintPhaseService.compareCbComplaintPhase(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ complaint }) => {
      this.complaint = complaint;
      if (complaint) {
        this.updateForm(complaint);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const complaint = this.complaintFormService.getComplaint(this.editForm);
    if (complaint.id !== null) {
      this.subscribeToSaveResponse(this.complaintService.update(complaint));
    } else {
      this.subscribeToSaveResponse(this.complaintService.create(complaint));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComplaint>>): void {
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

  protected updateForm(complaint: IComplaint): void {
    this.complaint = complaint;
    this.complaintFormService.resetForm(this.editForm, complaint);

    this.applicantsSharedCollection = this.applicantService.addApplicantToCollectionIfMissing<IApplicant>(
      this.applicantsSharedCollection,
      complaint.applicant,
    );
    this.cbComplaintFieldsSharedCollection = this.cbComplaintFieldService.addCbComplaintFieldToCollectionIfMissing<ICbComplaintField>(
      this.cbComplaintFieldsSharedCollection,
      complaint.cbComplaintField,
    );
    this.cbComplaintSubFieldsSharedCollection =
      this.cbComplaintSubFieldService.addCbComplaintSubFieldToCollectionIfMissing<ICbComplaintSubField>(
        this.cbComplaintSubFieldsSharedCollection,
        complaint.cbComplaintSubField,
      );
    this.cbComplaintTypesSharedCollection = this.cbComplaintTypeService.addCbComplaintTypeToCollectionIfMissing<ICbComplaintType>(
      this.cbComplaintTypesSharedCollection,
      complaint.cbComplaintType,
    );
    this.cbComplaintChannelsSharedCollection =
      this.cbComplaintChannelService.addCbComplaintChannelToCollectionIfMissing<ICbComplaintChannel>(
        this.cbComplaintChannelsSharedCollection,
        complaint.cbComplaintChannel,
      );
    this.cbApplicantCapacityTypesSharedCollection =
      this.cbApplicantCapacityTypeService.addCbApplicantCapacityTypeToCollectionIfMissing<ICbApplicantCapacityType>(
        this.cbApplicantCapacityTypesSharedCollection,
        complaint.cbApplicantCapacityType,
      );
    this.cbComplaintPhasesSharedCollection = this.cbComplaintPhaseService.addCbComplaintPhaseToCollectionIfMissing<ICbComplaintPhase>(
      this.cbComplaintPhasesSharedCollection,
      complaint.cbComplaintPhase,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.applicantService
      .query()
      .pipe(map((res: HttpResponse<IApplicant[]>) => res.body ?? []))
      .pipe(
        map((applicants: IApplicant[]) =>
          this.applicantService.addApplicantToCollectionIfMissing<IApplicant>(applicants, this.complaint?.applicant),
        ),
      )
      .subscribe((applicants: IApplicant[]) => (this.applicantsSharedCollection = applicants));

    this.cbComplaintFieldService
      .query()
      .pipe(map((res: HttpResponse<ICbComplaintField[]>) => res.body ?? []))
      .pipe(
        map((cbComplaintFields: ICbComplaintField[]) =>
          this.cbComplaintFieldService.addCbComplaintFieldToCollectionIfMissing<ICbComplaintField>(
            cbComplaintFields,
            this.complaint?.cbComplaintField,
          ),
        ),
      )
      .subscribe((cbComplaintFields: ICbComplaintField[]) => (this.cbComplaintFieldsSharedCollection = cbComplaintFields));

    this.cbComplaintSubFieldService
      .query()
      .pipe(map((res: HttpResponse<ICbComplaintSubField[]>) => res.body ?? []))
      .pipe(
        map((cbComplaintSubFields: ICbComplaintSubField[]) =>
          this.cbComplaintSubFieldService.addCbComplaintSubFieldToCollectionIfMissing<ICbComplaintSubField>(
            cbComplaintSubFields,
            this.complaint?.cbComplaintSubField,
          ),
        ),
      )
      .subscribe((cbComplaintSubFields: ICbComplaintSubField[]) => (this.cbComplaintSubFieldsSharedCollection = cbComplaintSubFields));

    this.cbComplaintTypeService
      .query()
      .pipe(map((res: HttpResponse<ICbComplaintType[]>) => res.body ?? []))
      .pipe(
        map((cbComplaintTypes: ICbComplaintType[]) =>
          this.cbComplaintTypeService.addCbComplaintTypeToCollectionIfMissing<ICbComplaintType>(
            cbComplaintTypes,
            this.complaint?.cbComplaintType,
          ),
        ),
      )
      .subscribe((cbComplaintTypes: ICbComplaintType[]) => (this.cbComplaintTypesSharedCollection = cbComplaintTypes));

    this.cbComplaintChannelService
      .query()
      .pipe(map((res: HttpResponse<ICbComplaintChannel[]>) => res.body ?? []))
      .pipe(
        map((cbComplaintChannels: ICbComplaintChannel[]) =>
          this.cbComplaintChannelService.addCbComplaintChannelToCollectionIfMissing<ICbComplaintChannel>(
            cbComplaintChannels,
            this.complaint?.cbComplaintChannel,
          ),
        ),
      )
      .subscribe((cbComplaintChannels: ICbComplaintChannel[]) => (this.cbComplaintChannelsSharedCollection = cbComplaintChannels));

    this.cbApplicantCapacityTypeService
      .query()
      .pipe(map((res: HttpResponse<ICbApplicantCapacityType[]>) => res.body ?? []))
      .pipe(
        map((cbApplicantCapacityTypes: ICbApplicantCapacityType[]) =>
          this.cbApplicantCapacityTypeService.addCbApplicantCapacityTypeToCollectionIfMissing<ICbApplicantCapacityType>(
            cbApplicantCapacityTypes,
            this.complaint?.cbApplicantCapacityType,
          ),
        ),
      )
      .subscribe(
        (cbApplicantCapacityTypes: ICbApplicantCapacityType[]) =>
          (this.cbApplicantCapacityTypesSharedCollection = cbApplicantCapacityTypes),
      );

    this.cbComplaintPhaseService
      .query()
      .pipe(map((res: HttpResponse<ICbComplaintPhase[]>) => res.body ?? []))
      .pipe(
        map((cbComplaintPhases: ICbComplaintPhase[]) =>
          this.cbComplaintPhaseService.addCbComplaintPhaseToCollectionIfMissing<ICbComplaintPhase>(
            cbComplaintPhases,
            this.complaint?.cbComplaintPhase,
          ),
        ),
      )
      .subscribe((cbComplaintPhases: ICbComplaintPhase[]) => (this.cbComplaintPhasesSharedCollection = cbComplaintPhases));
  }
}
