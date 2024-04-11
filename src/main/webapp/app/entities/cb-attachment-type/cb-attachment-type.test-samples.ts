import { ICbAttachmentType, NewCbAttachmentType } from './cb-attachment-type.model';

export const sampleWithRequiredData: ICbAttachmentType = {
  id: 21735,
};

export const sampleWithPartialData: ICbAttachmentType = {
  id: 10995,
  name: 'violent mob ah',
  extension: 'across',
};

export const sampleWithFullData: ICbAttachmentType = {
  id: 17969,
  name: 'needily',
  extension: 'pointed stipulate nonsense',
};

export const sampleWithNewData: NewCbAttachmentType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
