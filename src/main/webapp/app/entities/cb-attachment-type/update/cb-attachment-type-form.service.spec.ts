import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cb-attachment-type.test-samples';

import { CbAttachmentTypeFormService } from './cb-attachment-type-form.service';

describe('CbAttachmentType Form Service', () => {
  let service: CbAttachmentTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbAttachmentTypeFormService);
  });

  describe('Service methods', () => {
    describe('createCbAttachmentTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbAttachmentTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            extension: expect.any(Object),
          }),
        );
      });

      it('passing ICbAttachmentType should create a new form with FormGroup', () => {
        const formGroup = service.createCbAttachmentTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            extension: expect.any(Object),
          }),
        );
      });
    });

    describe('getCbAttachmentType', () => {
      it('should return NewCbAttachmentType for default CbAttachmentType initial value', () => {
        const formGroup = service.createCbAttachmentTypeFormGroup(sampleWithNewData);

        const cbAttachmentType = service.getCbAttachmentType(formGroup) as any;

        expect(cbAttachmentType).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbAttachmentType for empty CbAttachmentType initial value', () => {
        const formGroup = service.createCbAttachmentTypeFormGroup();

        const cbAttachmentType = service.getCbAttachmentType(formGroup) as any;

        expect(cbAttachmentType).toMatchObject({});
      });

      it('should return ICbAttachmentType', () => {
        const formGroup = service.createCbAttachmentTypeFormGroup(sampleWithRequiredData);

        const cbAttachmentType = service.getCbAttachmentType(formGroup) as any;

        expect(cbAttachmentType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbAttachmentType should not enable id FormControl', () => {
        const formGroup = service.createCbAttachmentTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbAttachmentType should disable id FormControl', () => {
        const formGroup = service.createCbAttachmentTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
