export interface ICbAttachmentType {
  id: number;
  name?: string | null;
  extension?: string | null;
}

export type NewCbAttachmentType = Omit<ICbAttachmentType, 'id'> & { id: null };
