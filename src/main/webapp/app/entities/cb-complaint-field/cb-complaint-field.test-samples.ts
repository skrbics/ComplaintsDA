import { ICbComplaintField, NewCbComplaintField } from './cb-complaint-field.model';

export const sampleWithRequiredData: ICbComplaintField = {
  id: 5657,
};

export const sampleWithPartialData: ICbComplaintField = {
  id: 11194,
  complaintFieldName: 'irritate',
};

export const sampleWithFullData: ICbComplaintField = {
  id: 25117,
  complaintFieldName: 'mmm in',
};

export const sampleWithNewData: NewCbComplaintField = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
