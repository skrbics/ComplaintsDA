import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cb-location.test-samples';

import { CbLocationFormService } from './cb-location-form.service';

describe('CbLocation Form Service', () => {
  let service: CbLocationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbLocationFormService);
  });

  describe('Service methods', () => {
    describe('createCbLocationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbLocationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            locationName: expect.any(Object),
            cbCity: expect.any(Object),
          }),
        );
      });

      it('passing ICbLocation should create a new form with FormGroup', () => {
        const formGroup = service.createCbLocationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            locationName: expect.any(Object),
            cbCity: expect.any(Object),
          }),
        );
      });
    });

    describe('getCbLocation', () => {
      it('should return NewCbLocation for default CbLocation initial value', () => {
        const formGroup = service.createCbLocationFormGroup(sampleWithNewData);

        const cbLocation = service.getCbLocation(formGroup) as any;

        expect(cbLocation).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbLocation for empty CbLocation initial value', () => {
        const formGroup = service.createCbLocationFormGroup();

        const cbLocation = service.getCbLocation(formGroup) as any;

        expect(cbLocation).toMatchObject({});
      });

      it('should return ICbLocation', () => {
        const formGroup = service.createCbLocationFormGroup(sampleWithRequiredData);

        const cbLocation = service.getCbLocation(formGroup) as any;

        expect(cbLocation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbLocation should not enable id FormControl', () => {
        const formGroup = service.createCbLocationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbLocation should disable id FormControl', () => {
        const formGroup = service.createCbLocationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
