import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../complaint-attachment.test-samples';

import { ComplaintAttachmentFormService } from './complaint-attachment-form.service';

describe('ComplaintAttachment Form Service', () => {
  let service: ComplaintAttachmentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComplaintAttachmentFormService);
  });

  describe('Service methods', () => {
    describe('createComplaintAttachmentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createComplaintAttachmentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordinalNo: expect.any(Object),
            name: expect.any(Object),
            path: expect.any(Object),
            complaint: expect.any(Object),
            cbAttachmentType: expect.any(Object),
          }),
        );
      });

      it('passing IComplaintAttachment should create a new form with FormGroup', () => {
        const formGroup = service.createComplaintAttachmentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordinalNo: expect.any(Object),
            name: expect.any(Object),
            path: expect.any(Object),
            complaint: expect.any(Object),
            cbAttachmentType: expect.any(Object),
          }),
        );
      });
    });

    describe('getComplaintAttachment', () => {
      it('should return NewComplaintAttachment for default ComplaintAttachment initial value', () => {
        const formGroup = service.createComplaintAttachmentFormGroup(sampleWithNewData);

        const complaintAttachment = service.getComplaintAttachment(formGroup) as any;

        expect(complaintAttachment).toMatchObject(sampleWithNewData);
      });

      it('should return NewComplaintAttachment for empty ComplaintAttachment initial value', () => {
        const formGroup = service.createComplaintAttachmentFormGroup();

        const complaintAttachment = service.getComplaintAttachment(formGroup) as any;

        expect(complaintAttachment).toMatchObject({});
      });

      it('should return IComplaintAttachment', () => {
        const formGroup = service.createComplaintAttachmentFormGroup(sampleWithRequiredData);

        const complaintAttachment = service.getComplaintAttachment(formGroup) as any;

        expect(complaintAttachment).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IComplaintAttachment should not enable id FormControl', () => {
        const formGroup = service.createComplaintAttachmentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewComplaintAttachment should disable id FormControl', () => {
        const formGroup = service.createComplaintAttachmentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
