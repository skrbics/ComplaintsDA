import { IApplicant, NewApplicant } from './applicant.model';

export const sampleWithRequiredData: IApplicant = {
  id: 13016,
};

export const sampleWithPartialData: IApplicant = {
  id: 29971,
  middleName: 'unfortunately er following',
  lastName: 'Hintz',
  email: 'Dennis14@gmail.com',
  phone: '(693) 381-6638 x68568',
};

export const sampleWithFullData: IApplicant = {
  id: 26752,
  firstName: 'Cleveland',
  middleName: 'and',
  lastName: 'Franey',
  email: 'Jevon_Wunsch71@gmail.com',
  phone: '345.567.7503 x24194',
};

export const sampleWithNewData: NewApplicant = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
