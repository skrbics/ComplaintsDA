import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cb-complaint-field.test-samples';

import { CbComplaintFieldFormService } from './cb-complaint-field-form.service';

describe('CbComplaintField Form Service', () => {
  let service: CbComplaintFieldFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbComplaintFieldFormService);
  });

  describe('Service methods', () => {
    describe('createCbComplaintFieldFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbComplaintFieldFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            complaintFieldName: expect.any(Object),
          }),
        );
      });

      it('passing ICbComplaintField should create a new form with FormGroup', () => {
        const formGroup = service.createCbComplaintFieldFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            complaintFieldName: expect.any(Object),
          }),
        );
      });
    });

    describe('getCbComplaintField', () => {
      it('should return NewCbComplaintField for default CbComplaintField initial value', () => {
        const formGroup = service.createCbComplaintFieldFormGroup(sampleWithNewData);

        const cbComplaintField = service.getCbComplaintField(formGroup) as any;

        expect(cbComplaintField).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbComplaintField for empty CbComplaintField initial value', () => {
        const formGroup = service.createCbComplaintFieldFormGroup();

        const cbComplaintField = service.getCbComplaintField(formGroup) as any;

        expect(cbComplaintField).toMatchObject({});
      });

      it('should return ICbComplaintField', () => {
        const formGroup = service.createCbComplaintFieldFormGroup(sampleWithRequiredData);

        const cbComplaintField = service.getCbComplaintField(formGroup) as any;

        expect(cbComplaintField).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbComplaintField should not enable id FormControl', () => {
        const formGroup = service.createCbComplaintFieldFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbComplaintField should disable id FormControl', () => {
        const formGroup = service.createCbComplaintFieldFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
