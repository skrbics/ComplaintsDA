import { ICbCountry, NewCbCountry } from './cb-country.model';

export const sampleWithRequiredData: ICbCountry = {
  id: 23554,
};

export const sampleWithPartialData: ICbCountry = {
  id: 14583,
  name: 'oof',
};

export const sampleWithFullData: ICbCountry = {
  id: 21865,
  name: 'overvalue revelation versus',
  abbreviation: 'to unfurl',
};

export const sampleWithNewData: NewCbCountry = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
