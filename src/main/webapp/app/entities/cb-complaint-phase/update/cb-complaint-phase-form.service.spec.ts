import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cb-complaint-phase.test-samples';

import { CbComplaintPhaseFormService } from './cb-complaint-phase-form.service';

describe('CbComplaintPhase Form Service', () => {
  let service: CbComplaintPhaseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CbComplaintPhaseFormService);
  });

  describe('Service methods', () => {
    describe('createCbComplaintPhaseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCbComplaintPhaseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordinalNo: expect.any(Object),
            complaintPhaseName: expect.any(Object),
          }),
        );
      });

      it('passing ICbComplaintPhase should create a new form with FormGroup', () => {
        const formGroup = service.createCbComplaintPhaseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordinalNo: expect.any(Object),
            complaintPhaseName: expect.any(Object),
          }),
        );
      });
    });

    describe('getCbComplaintPhase', () => {
      it('should return NewCbComplaintPhase for default CbComplaintPhase initial value', () => {
        const formGroup = service.createCbComplaintPhaseFormGroup(sampleWithNewData);

        const cbComplaintPhase = service.getCbComplaintPhase(formGroup) as any;

        expect(cbComplaintPhase).toMatchObject(sampleWithNewData);
      });

      it('should return NewCbComplaintPhase for empty CbComplaintPhase initial value', () => {
        const formGroup = service.createCbComplaintPhaseFormGroup();

        const cbComplaintPhase = service.getCbComplaintPhase(formGroup) as any;

        expect(cbComplaintPhase).toMatchObject({});
      });

      it('should return ICbComplaintPhase', () => {
        const formGroup = service.createCbComplaintPhaseFormGroup(sampleWithRequiredData);

        const cbComplaintPhase = service.getCbComplaintPhase(formGroup) as any;

        expect(cbComplaintPhase).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICbComplaintPhase should not enable id FormControl', () => {
        const formGroup = service.createCbComplaintPhaseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCbComplaintPhase should disable id FormControl', () => {
        const formGroup = service.createCbComplaintPhaseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
