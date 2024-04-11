import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbCity } from 'app/entities/cb-city/cb-city.model';
import { CbCityService } from 'app/entities/cb-city/service/cb-city.service';
import { ICbCountry } from 'app/entities/cb-country/cb-country.model';
import { CbCountryService } from 'app/entities/cb-country/service/cb-country.service';
import { AddressService } from '../service/address.service';
import { IAddress } from '../address.model';
import { AddressFormService, AddressFormGroup } from './address-form.service';

@Component({
  standalone: true,
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  address: IAddress | null = null;

  cbCitiesSharedCollection: ICbCity[] = [];
  cbCountriesSharedCollection: ICbCountry[] = [];

  protected addressService = inject(AddressService);
  protected addressFormService = inject(AddressFormService);
  protected cbCityService = inject(CbCityService);
  protected cbCountryService = inject(CbCountryService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AddressFormGroup = this.addressFormService.createAddressFormGroup();

  compareCbCity = (o1: ICbCity | null, o2: ICbCity | null): boolean => this.cbCityService.compareCbCity(o1, o2);

  compareCbCountry = (o1: ICbCountry | null, o2: ICbCountry | null): boolean => this.cbCountryService.compareCbCountry(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.address = address;
      if (address) {
        this.updateForm(address);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.addressFormService.getAddress(this.editForm);
    if (address.id !== null) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
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

  protected updateForm(address: IAddress): void {
    this.address = address;
    this.addressFormService.resetForm(this.editForm, address);

    this.cbCitiesSharedCollection = this.cbCityService.addCbCityToCollectionIfMissing<ICbCity>(
      this.cbCitiesSharedCollection,
      address.cbCity,
    );
    this.cbCountriesSharedCollection = this.cbCountryService.addCbCountryToCollectionIfMissing<ICbCountry>(
      this.cbCountriesSharedCollection,
      address.cbCountry,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cbCityService
      .query()
      .pipe(map((res: HttpResponse<ICbCity[]>) => res.body ?? []))
      .pipe(map((cbCities: ICbCity[]) => this.cbCityService.addCbCityToCollectionIfMissing<ICbCity>(cbCities, this.address?.cbCity)))
      .subscribe((cbCities: ICbCity[]) => (this.cbCitiesSharedCollection = cbCities));

    this.cbCountryService
      .query()
      .pipe(map((res: HttpResponse<ICbCountry[]>) => res.body ?? []))
      .pipe(
        map((cbCountries: ICbCountry[]) =>
          this.cbCountryService.addCbCountryToCollectionIfMissing<ICbCountry>(cbCountries, this.address?.cbCountry),
        ),
      )
      .subscribe((cbCountries: ICbCountry[]) => (this.cbCountriesSharedCollection = cbCountries));
  }
}
