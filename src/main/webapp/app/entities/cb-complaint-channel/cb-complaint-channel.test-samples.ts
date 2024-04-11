import { ICbComplaintChannel, NewCbComplaintChannel } from './cb-complaint-channel.model';

export const sampleWithRequiredData: ICbComplaintChannel = {
  id: 30687,
};

export const sampleWithPartialData: ICbComplaintChannel = {
  id: 5745,
};

export const sampleWithFullData: ICbComplaintChannel = {
  id: 25626,
  complaintChannelName: 'of',
};

export const sampleWithNewData: NewCbComplaintChannel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
