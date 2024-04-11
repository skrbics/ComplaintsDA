import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 1003,
};

export const sampleWithPartialData: IAddress = {
  id: 7501,
  street: 'Skyline Drive',
};

export const sampleWithFullData: IAddress = {
  id: 14748,
  street: 'Old Military Road',
  houseNo: 'how after',
};

export const sampleWithNewData: NewAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
