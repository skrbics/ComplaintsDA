import { ICbComplaintPhase, NewCbComplaintPhase } from './cb-complaint-phase.model';

export const sampleWithRequiredData: ICbComplaintPhase = {
  id: 3156,
};

export const sampleWithPartialData: ICbComplaintPhase = {
  id: 10350,
  ordinalNo: 31173,
};

export const sampleWithFullData: ICbComplaintPhase = {
  id: 4938,
  ordinalNo: 16853,
  complaintPhaseName: 'ad advanced between',
};

export const sampleWithNewData: NewCbComplaintPhase = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
