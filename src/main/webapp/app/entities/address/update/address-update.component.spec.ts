import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICbCity } from 'app/entities/cb-city/cb-city.model';
import { CbCityService } from 'app/entities/cb-city/service/cb-city.service';
import { ICbCountry } from 'app/entities/cb-country/cb-country.model';
import { CbCountryService } from 'app/entities/cb-country/service/cb-country.service';
import { IAddress } from '../address.model';
import { AddressService } from '../service/address.service';
import { AddressFormService } from './address-form.service';

import { AddressUpdateComponent } from './address-update.component';

describe('Address Management Update Component', () => {
  let comp: AddressUpdateComponent;
  let fixture: ComponentFixture<AddressUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let addressFormService: AddressFormService;
  let addressService: AddressService;
  let cbCityService: CbCityService;
  let cbCountryService: CbCountryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AddressUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AddressUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AddressUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addressFormService = TestBed.inject(AddressFormService);
    addressService = TestBed.inject(AddressService);
    cbCityService = TestBed.inject(CbCityService);
    cbCountryService = TestBed.inject(CbCountryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CbCity query and add missing value', () => {
      const address: IAddress = { id: 456 };
      const cbCity: ICbCity = { id: 23203 };
      address.cbCity = cbCity;

      const cbCityCollection: ICbCity[] = [{ id: 26855 }];
      jest.spyOn(cbCityService, 'query').mockReturnValue(of(new HttpResponse({ body: cbCityCollection })));
      const additionalCbCities = [cbCity];
      const expectedCollection: ICbCity[] = [...additionalCbCities, ...cbCityCollection];
      jest.spyOn(cbCityService, 'addCbCityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ address });
      comp.ngOnInit();

      expect(cbCityService.query).toHaveBeenCalled();
      expect(cbCityService.addCbCityToCollectionIfMissing).toHaveBeenCalledWith(
        cbCityCollection,
        ...additionalCbCities.map(expect.objectContaining),
      );
      expect(comp.cbCitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CbCountry query and add missing value', () => {
      const address: IAddress = { id: 456 };
      const cbCountry: ICbCountry = { id: 19583 };
      address.cbCountry = cbCountry;

      const cbCountryCollection: ICbCountry[] = [{ id: 3669 }];
      jest.spyOn(cbCountryService, 'query').mockReturnValue(of(new HttpResponse({ body: cbCountryCollection })));
      const additionalCbCountries = [cbCountry];
      const expectedCollection: ICbCountry[] = [...additionalCbCountries, ...cbCountryCollection];
      jest.spyOn(cbCountryService, 'addCbCountryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ address });
      comp.ngOnInit();

      expect(cbCountryService.query).toHaveBeenCalled();
      expect(cbCountryService.addCbCountryToCollectionIfMissing).toHaveBeenCalledWith(
        cbCountryCollection,
        ...additionalCbCountries.map(expect.objectContaining),
      );
      expect(comp.cbCountriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const address: IAddress = { id: 456 };
      const cbCity: ICbCity = { id: 6801 };
      address.cbCity = cbCity;
      const cbCountry: ICbCountry = { id: 30031 };
      address.cbCountry = cbCountry;

      activatedRoute.data = of({ address });
      comp.ngOnInit();

      expect(comp.cbCitiesSharedCollection).toContain(cbCity);
      expect(comp.cbCountriesSharedCollection).toContain(cbCountry);
      expect(comp.address).toEqual(address);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddress>>();
      const address = { id: 123 };
      jest.spyOn(addressFormService, 'getAddress').mockReturnValue(address);
      jest.spyOn(addressService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ address });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: address }));
      saveSubject.complete();

      // THEN
      expect(addressFormService.getAddress).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(addressService.update).toHaveBeenCalledWith(expect.objectContaining(address));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddress>>();
      const address = { id: 123 };
      jest.spyOn(addressFormService, 'getAddress').mockReturnValue({ id: null });
      jest.spyOn(addressService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ address: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: address }));
      saveSubject.complete();

      // THEN
      expect(addressFormService.getAddress).toHaveBeenCalled();
      expect(addressService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAddress>>();
      const address = { id: 123 };
      jest.spyOn(addressService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ address });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addressService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCbCity', () => {
      it('Should forward to cbCityService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cbCityService, 'compareCbCity');
        comp.compareCbCity(entity, entity2);
        expect(cbCityService.compareCbCity).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCbCountry', () => {
      it('Should forward to cbCountryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cbCountryService, 'compareCbCountry');
        comp.compareCbCountry(entity, entity2);
        expect(cbCountryService.compareCbCountry).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
