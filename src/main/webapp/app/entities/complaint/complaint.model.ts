import dayjs from 'dayjs/esm';
import { IApplicant } from 'app/entities/applicant/applicant.model';
import { ICbComplaintField } from 'app/entities/cb-complaint-field/cb-complaint-field.model';
import { ICbComplaintSubField } from 'app/entities/cb-complaint-sub-field/cb-complaint-sub-field.model';
import { ICbComplaintType } from 'app/entities/cb-complaint-type/cb-complaint-type.model';
import { ICbComplaintChannel } from 'app/entities/cb-complaint-channel/cb-complaint-channel.model';
import { ICbApplicantCapacityType } from 'app/entities/cb-applicant-capacity-type/cb-applicant-capacity-type.model';
import { ICbComplaintPhase } from 'app/entities/cb-complaint-phase/cb-complaint-phase.model';

export interface IComplaint {
  id: number;
  complaintText?: string | null;
  dateAndTime?: dayjs.Dayjs | null;
  writtenReplyBySMS?: boolean | null;
  applicant?: Pick<IApplicant, 'id'> | null;
  cbComplaintField?: Pick<ICbComplaintField, 'id'> | null;
  cbComplaintSubField?: Pick<ICbComplaintSubField, 'id'> | null;
  cbComplaintType?: Pick<ICbComplaintType, 'id'> | null;
  cbComplaintChannel?: Pick<ICbComplaintChannel, 'id'> | null;
  cbApplicantCapacityType?: Pick<ICbApplicantCapacityType, 'id'> | null;
  cbComplaintPhase?: Pick<ICbComplaintPhase, 'id'> | null;
}

export type NewComplaint = Omit<IComplaint, 'id'> & { id: null };
