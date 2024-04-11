import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbCity } from 'app/entities/cb-city/cb-city.model';
import { CbCityService } from 'app/entities/cb-city/service/cb-city.service';
import { ICbLocation } from '../cb-location.model';
import { CbLocationService } from '../service/cb-location.service';
import { CbLocationFormService, CbLocationFormGroup } from './cb-location-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cb-location-update',
  templateUrl: './cb-location-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CbLocationUpdateComponent implements OnInit {
  isSaving = false;
  cbLocation: ICbLocation | null = null;

  cbCitiesSharedCollection: ICbCity[] = [];

  protected cbLocationService = inject(CbLocationService);
  protected cbLocationFormService = inject(CbLocationFormService);
  protected cbCityService = inject(CbCityService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CbLocationFormGroup = this.cbLocationFormService.createCbLocationFormGroup();

  compareCbCity = (o1: ICbCity | null, o2: ICbCity | null): boolean => this.cbCityService.compareCbCity(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbLocation }) => {
      this.cbLocation = cbLocation;
      if (cbLocation) {
        this.updateForm(cbLocation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbLocation = this.cbLocationFormService.getCbLocation(this.editForm);
    if (cbLocation.id !== null) {
      this.subscribeToSaveResponse(this.cbLocationService.update(cbLocation));
    } else {
      this.subscribeToSaveResponse(this.cbLocationService.create(cbLocation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbLocation>>): void {
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

  protected updateForm(cbLocation: ICbLocation): void {
    this.cbLocation = cbLocation;
    this.cbLocationFormService.resetForm(this.editForm, cbLocation);

    this.cbCitiesSharedCollection = this.cbCityService.addCbCityToCollectionIfMissing<ICbCity>(
      this.cbCitiesSharedCollection,
      cbLocation.cbCity,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cbCityService
      .query()
      .pipe(map((res: HttpResponse<ICbCity[]>) => res.body ?? []))
      .pipe(map((cbCities: ICbCity[]) => this.cbCityService.addCbCityToCollectionIfMissing<ICbCity>(cbCities, this.cbLocation?.cbCity)))
      .subscribe((cbCities: ICbCity[]) => (this.cbCitiesSharedCollection = cbCities));
  }
}
