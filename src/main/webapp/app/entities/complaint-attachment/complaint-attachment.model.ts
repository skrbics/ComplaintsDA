import { IComplaint } from 'app/entities/complaint/complaint.model';
import { ICbAttachmentType } from 'app/entities/cb-attachment-type/cb-attachment-type.model';

export interface IComplaintAttachment {
  id: number;
  ordinalNo?: number | null;
  name?: string | null;
  path?: string | null;
  complaint?: Pick<IComplaint, 'id'> | null;
  cbAttachmentType?: Pick<ICbAttachmentType, 'id'> | null;
}

export type NewComplaintAttachment = Omit<IComplaintAttachment, 'id'> & { id: null };
