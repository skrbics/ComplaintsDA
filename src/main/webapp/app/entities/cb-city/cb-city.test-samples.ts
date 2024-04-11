import { ICbCity, NewCbCity } from './cb-city.model';

export const sampleWithRequiredData: ICbCity = {
  id: 1722,
};

export const sampleWithPartialData: ICbCity = {
  id: 17969,
  name: 'unwieldy midst uselessly',
  zip: 'script mad',
};

export const sampleWithFullData: ICbCity = {
  id: 18425,
  name: 'brr blissfully',
  zip: 'yum questioningly ugh',
};

export const sampleWithNewData: NewCbCity = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
