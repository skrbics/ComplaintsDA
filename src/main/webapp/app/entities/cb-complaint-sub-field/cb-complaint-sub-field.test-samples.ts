import { ICbComplaintSubField, NewCbComplaintSubField } from './cb-complaint-sub-field.model';

export const sampleWithRequiredData: ICbComplaintSubField = {
  id: 12247,
};

export const sampleWithPartialData: ICbComplaintSubField = {
  id: 8505,
  complaintSubFieldName: 'sign unwieldy yippee',
};

export const sampleWithFullData: ICbComplaintSubField = {
  id: 9663,
  complaintSubFieldName: 'oof verification next',
};

export const sampleWithNewData: NewCbComplaintSubField = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
