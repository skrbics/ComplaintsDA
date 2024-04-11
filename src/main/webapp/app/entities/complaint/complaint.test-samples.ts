import dayjs from 'dayjs/esm';

import { IComplaint, NewComplaint } from './complaint.model';

export const sampleWithRequiredData: IComplaint = {
  id: 17483,
};

export const sampleWithPartialData: IComplaint = {
  id: 24325,
  complaintText: 'whoa ah',
  writtenReplyBySMS: false,
};

export const sampleWithFullData: IComplaint = {
  id: 29820,
  complaintText: 'quietly infuse suddenly',
  dateAndTime: dayjs('2024-04-11T05:12'),
  writtenReplyBySMS: false,
};

export const sampleWithNewData: NewComplaint = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
