import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cb-applicant-capacity-type.test-samples';

import { CbApplicantCapacityTypeFormService } from './cb-applicant-capacity-type-form.service';

describe('CbApplicantCapacityType Form Service', () => {
  let service: CbApplicantCapacityTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbApplicantCapacityTypeFormService);
  });

  describe('Service methods', () => {
    describe('createCbApplicantCapacityTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbApplicantCapacityTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            applicantCapacityTypeName: expect.any(Object),
          }),
        );
      });

      it('passing ICbApplicantCapacityType should create a new form with FormGroup', () => {
        const formGroup = service.createCbApplicantCapacityTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            applicantCapacityTypeName: expect.any(Object),
          }),
        );
      });
    });

    describe('getCbApplicantCapacityType', () => {
      it('should return NewCbApplicantCapacityType for default CbApplicantCapacityType initial value', () => {
        const formGroup = service.createCbApplicantCapacityTypeFormGroup(sampleWithNewData);

        const cbApplicantCapacityType = service.getCbApplicantCapacityType(formGroup) as any;

        expect(cbApplicantCapacityType).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbApplicantCapacityType for empty CbApplicantCapacityType initial value', () => {
        const formGroup = service.createCbApplicantCapacityTypeFormGroup();

        const cbApplicantCapacityType = service.getCbApplicantCapacityType(formGroup) as any;

        expect(cbApplicantCapacityType).toMatchObject({});
      });

      it('should return ICbApplicantCapacityType', () => {
        const formGroup = service.createCbApplicantCapacityTypeFormGroup(sampleWithRequiredData);

        const cbApplicantCapacityType = service.getCbApplicantCapacityType(formGroup) as any;

        expect(cbApplicantCapacityType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbApplicantCapacityType should not enable id FormControl', () => {
        const formGroup = service.createCbApplicantCapacityTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbApplicantCapacityType should disable id FormControl', () => {
        const formGroup = service.createCbApplicantCapacityTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
