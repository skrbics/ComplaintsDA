import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '1bec70b9-3f18-4b9e-8aea-273c28394d23',
};

export const sampleWithPartialData: IAuthority = {
  name: '9e0dc02b-811b-4489-b7e5-1ca6105b9793',
};

export const sampleWithFullData: IAuthority = {
  name: '5fb7c983-4fbe-4452-9c42-53ca295a4eb0',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
