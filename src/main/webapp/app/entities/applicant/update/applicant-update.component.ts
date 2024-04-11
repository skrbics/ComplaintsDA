import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IApplicant } from '../applicant.model';
import { ApplicantService } from '../service/applicant.service';
import { ApplicantFormService, ApplicantFormGroup } from './applicant-form.service';

@Component({
  standalone: true,
  selector: 'jhi-applicant-update',
  templateUrl: './applicant-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ApplicantUpdateComponent implements OnInit {
  isSaving = false;
  applicant: IApplicant | null = null;

  addressesCollection: IAddress[] = [];

  protected applicantService = inject(ApplicantService);
  protected applicantFormService = inject(ApplicantFormService);
  protected addressService = inject(AddressService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ApplicantFormGroup = this.applicantFormService.createApplicantFormGroup();

  compareAddress = (o1: IAddress | null, o2: IAddress | null): boolean => this.addressService.compareAddress(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicant }) => {
      this.applicant = applicant;
      if (applicant) {
        this.updateForm(applicant);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicant = this.applicantFormService.getApplicant(this.editForm);
    if (applicant.id !== null) {
      this.subscribeToSaveResponse(this.applicantService.update(applicant));
    } else {
      this.subscribeToSaveResponse(this.applicantService.create(applicant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicant>>): void {
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

  protected updateForm(applicant: IApplicant): void {
    this.applicant = applicant;
    this.applicantFormService.resetForm(this.editForm, applicant);

    this.addressesCollection = this.addressService.addAddressToCollectionIfMissing<IAddress>(this.addressesCollection, applicant.address);
  }

  protected loadRelationshipsOptions(): void {
    this.addressService
      .query({ filter: 'applicant-is-null' })
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) => this.addressService.addAddressToCollectionIfMissing<IAddress>(addresses, this.applicant?.address)),
      )
      .subscribe((addresses: IAddress[]) => (this.addressesCollection = addresses));
  }
}
