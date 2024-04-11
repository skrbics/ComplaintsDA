import { ICbApplicantCapacityType, NewCbApplicantCapacityType } from './cb-applicant-capacity-type.model';

export const sampleWithRequiredData: ICbApplicantCapacityType = {
  id: 19390,
};

export const sampleWithPartialData: ICbApplicantCapacityType = {
  id: 23417,
};

export const sampleWithFullData: ICbApplicantCapacityType = {
  id: 32372,
  applicantCapacityTypeName: 'elementary die big',
};

export const sampleWithNewData: NewCbApplicantCapacityType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
