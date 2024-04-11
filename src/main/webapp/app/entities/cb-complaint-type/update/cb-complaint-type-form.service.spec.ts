import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cb-complaint-type.test-samples';

import { CbComplaintTypeFormService } from './cb-complaint-type-form.service';

describe('CbComplaintType Form Service', () => {
  let service: CbComplaintTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbComplaintTypeFormService);
  });

  describe('Service methods', () => {
    describe('createCbComplaintTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbComplaintTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            complaintTypeName: expect.any(Object),
          }),
        );
      });

      it('passing ICbComplaintType should create a new form with FormGroup', () => {
        const formGroup = service.createCbComplaintTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            complaintTypeName: expect.any(Object),
          }),
        );
      });
    });

    describe('getCbComplaintType', () => {
      it('should return NewCbComplaintType for default CbComplaintType initial value', () => {
        const formGroup = service.createCbComplaintTypeFormGroup(sampleWithNewData);

        const cbComplaintType = service.getCbComplaintType(formGroup) as any;

        expect(cbComplaintType).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbComplaintType for empty CbComplaintType initial value', () => {
        const formGroup = service.createCbComplaintTypeFormGroup();

        const cbComplaintType = service.getCbComplaintType(formGroup) as any;

        expect(cbComplaintType).toMatchObject({});
      });

      it('should return ICbComplaintType', () => {
        const formGroup = service.createCbComplaintTypeFormGroup(sampleWithRequiredData);

        const cbComplaintType = service.getCbComplaintType(formGroup) as any;

        expect(cbComplaintType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbComplaintType should not enable id FormControl', () => {
        const formGroup = service.createCbComplaintTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbComplaintType should disable id FormControl', () => {
        const formGroup = service.createCbComplaintTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
