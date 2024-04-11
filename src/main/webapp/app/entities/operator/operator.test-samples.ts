import { IOperator, NewOperator } from './operator.model';

export const sampleWithRequiredData: IOperator = {
  id: 16685,
};

export const sampleWithPartialData: IOperator = {
  id: 2471,
  middleName: 'even energetically merit',
  lastName: 'Wunsch',
  email: 'Lowell.Sawayn-Hand@hotmail.com',
  phone: '328-572-6313 x869',
};

export const sampleWithFullData: IOperator = {
  id: 5550,
  firstName: 'Novella',
  middleName: 'fencing salvage',
  lastName: 'Schuppe',
  email: 'Gail.Jacobson4@hotmail.com',
  phone: '776.931.4979 x7547',
};

export const sampleWithNewData: NewOperator = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
