import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cb-complaint-channel.test-samples';

import { CbComplaintChannelFormService } from './cb-complaint-channel-form.service';

describe('CbComplaintChannel Form Service', () => {
  let service: CbComplaintChannelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbComplaintChannelFormService);
  });

  describe('Service methods', () => {
    describe('createCbComplaintChannelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbComplaintChannelFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            complaintChannelName: expect.any(Object),
          }),
        );
      });

      it('passing ICbComplaintChannel should create a new form with FormGroup', () => {
        const formGroup = service.createCbComplaintChannelFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            complaintChannelName: expect.any(Object),
          }),
        );
      });
    });

    describe('getCbComplaintChannel', () => {
      it('should return NewCbComplaintChannel for default CbComplaintChannel initial value', () => {
        const formGroup = service.createCbComplaintChannelFormGroup(sampleWithNewData);

        const cbComplaintChannel = service.getCbComplaintChannel(formGroup) as any;

        expect(cbComplaintChannel).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbComplaintChannel for empty CbComplaintChannel initial value', () => {
        const formGroup = service.createCbComplaintChannelFormGroup();

        const cbComplaintChannel = service.getCbComplaintChannel(formGroup) as any;

        expect(cbComplaintChannel).toMatchObject({});
      });

      it('should return ICbComplaintChannel', () => {
        const formGroup = service.createCbComplaintChannelFormGroup(sampleWithRequiredData);

        const cbComplaintChannel = service.getCbComplaintChannel(formGroup) as any;

        expect(cbComplaintChannel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbComplaintChannel should not enable id FormControl', () => {
        const formGroup = service.createCbComplaintChannelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbComplaintChannel should disable id FormControl', () => {
        const formGroup = service.createCbComplaintChannelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
