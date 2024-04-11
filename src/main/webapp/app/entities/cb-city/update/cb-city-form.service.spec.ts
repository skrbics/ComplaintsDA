import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cb-city.test-samples';

import { CbCityFormService } from './cb-city-form.service';

describe('CbCity Form Service', () => {
  let service: CbCityFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbCityFormService);
  });

  describe('Service methods', () => {
    describe('createCbCityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbCityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            zip: expect.any(Object),
          }),
        );
      });

      it('passing ICbCity should create a new form with FormGroup', () => {
        const formGroup = service.createCbCityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            zip: expect.any(Object),
          }),
        );
      });
    });

    describe('getCbCity', () => {
      it('should return NewCbCity for default CbCity initial value', () => {
        const formGroup = service.createCbCityFormGroup(sampleWithNewData);

        const cbCity = service.getCbCity(formGroup) as any;

        expect(cbCity).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbCity for empty CbCity initial value', () => {
        const formGroup = service.createCbCityFormGroup();

        const cbCity = service.getCbCity(formGroup) as any;

        expect(cbCity).toMatchObject({});
      });

      it('should return ICbCity', () => {
        const formGroup = service.createCbCityFormGroup(sampleWithRequiredData);

        const cbCity = service.getCbCity(formGroup) as any;

        expect(cbCity).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbCity should not enable id FormControl', () => {
        const formGroup = service.createCbCityFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbCity should disable id FormControl', () => {
        const formGroup = service.createCbCityFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
