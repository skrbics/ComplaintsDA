import { ICbLocation, NewCbLocation } from './cb-location.model';

export const sampleWithRequiredData: ICbLocation = {
  id: 29467,
};

export const sampleWithPartialData: ICbLocation = {
  id: 25332,
};

export const sampleWithFullData: ICbLocation = {
  id: 13016,
  locationName: 'hasty scary powerfully',
};

export const sampleWithNewData: NewCbLocation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
