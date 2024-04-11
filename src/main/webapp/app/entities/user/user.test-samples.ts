import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 27045,
  login: 'b@qno_',
};

export const sampleWithPartialData: IUser = {
  id: 16876,
  login: '16=L@4Mu\\!A7Cb\\&P',
};

export const sampleWithFullData: IUser = {
  id: 32548,
  login: '.E4mo!@5f3j4\\EvmNFeg\\"o9G\\,cRlCU\\-mK',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
