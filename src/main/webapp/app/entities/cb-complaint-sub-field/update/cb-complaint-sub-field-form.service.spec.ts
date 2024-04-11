import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cb-complaint-sub-field.test-samples';

import { CbComplaintSubFieldFormService } from './cb-complaint-sub-field-form.service';

describe('CbComplaintSubField Form Service', () => {
  let service: CbComplaintSubFieldFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbComplaintSubFieldFormService);
  });

  describe('Service methods', () => {
    describe('createCbComplaintSubFieldFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbComplaintSubFieldFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            complaintSubFieldName: expect.any(Object),
          }),
        );
      });

      it('passing ICbComplaintSubField should create a new form with FormGroup', () => {
        const formGroup = service.createCbComplaintSubFieldFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            complaintSubFieldName: expect.any(Object),
          }),
        );
      });
    });

    describe('getCbComplaintSubField', () => {
      it('should return NewCbComplaintSubField for default CbComplaintSubField initial value', () => {
        const formGroup = service.createCbComplaintSubFieldFormGroup(sampleWithNewData);

        const cbComplaintSubField = service.getCbComplaintSubField(formGroup) as any;

        expect(cbComplaintSubField).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbComplaintSubField for empty CbComplaintSubField initial value', () => {
        const formGroup = service.createCbComplaintSubFieldFormGroup();

        const cbComplaintSubField = service.getCbComplaintSubField(formGroup) as any;

        expect(cbComplaintSubField).toMatchObject({});
      });

      it('should return ICbComplaintSubField', () => {
        const formGroup = service.createCbComplaintSubFieldFormGroup(sampleWithRequiredData);

        const cbComplaintSubField = service.getCbComplaintSubField(formGroup) as any;

        expect(cbComplaintSubField).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbComplaintSubField should not enable id FormControl', () => {
        const formGroup = service.createCbComplaintSubFieldFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbComplaintSubField should disable id FormControl', () => {
        const formGroup = service.createCbComplaintSubFieldFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
