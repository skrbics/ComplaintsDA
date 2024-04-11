import { IComplaintAttachment, NewComplaintAttachment } from './complaint-attachment.model';

export const sampleWithRequiredData: IComplaintAttachment = {
  id: 13132,
};

export const sampleWithPartialData: IComplaintAttachment = {
  id: 5046,
  path: 'fluid',
};

export const sampleWithFullData: IComplaintAttachment = {
  id: 17994,
  ordinalNo: 12969,
  name: 'heavily',
  path: 'heist generally clapboard',
};

export const sampleWithNewData: NewComplaintAttachment = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
