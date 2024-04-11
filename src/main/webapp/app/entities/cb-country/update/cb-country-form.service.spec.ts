import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cb-country.test-samples';

import { CbCountryFormService } from './cb-country-form.service';

describe('CbCountry Form Service', () => {
  let service: CbCountryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbCountryFormService);
  });

  describe('Service methods', () => {
    describe('createCbCountryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbCountryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            abbreviation: expect.any(Object),
          }),
        );
      });

      it('passing ICbCountry should create a new form with FormGroup', () => {
        const formGroup = service.createCbCountryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            abbreviation: expect.any(Object),
          }),
        );
      });
    });

    describe('getCbCountry', () => {
      it('should return NewCbCountry for default CbCountry initial value', () => {
        const formGroup = service.createCbCountryFormGroup(sampleWithNewData);

        const cbCountry = service.getCbCountry(formGroup) as any;

        expect(cbCountry).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbCountry for empty CbCountry initial value', () => {
        const formGroup = service.createCbCountryFormGroup();

        const cbCountry = service.getCbCountry(formGroup) as any;

        expect(cbCountry).toMatchObject({});
      });

      it('should return ICbCountry', () => {
        const formGroup = service.createCbCountryFormGroup(sampleWithRequiredData);

        const cbCountry = service.getCbCountry(formGroup) as any;

        expect(cbCountry).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbCountry should not enable id FormControl', () => {
        const formGroup = service.createCbCountryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbCountry should disable id FormControl', () => {
        const formGroup = service.createCbCountryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
