import { ICbComplaintType, NewCbComplaintType } from './cb-complaint-type.model';

export const sampleWithRequiredData: ICbComplaintType = {
  id: 6389,
};

export const sampleWithPartialData: ICbComplaintType = {
  id: 17804,
};

export const sampleWithFullData: ICbComplaintType = {
  id: 28067,
  complaintTypeName: 'hearty raise till',
};

export const sampleWithNewData: NewCbComplaintType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
