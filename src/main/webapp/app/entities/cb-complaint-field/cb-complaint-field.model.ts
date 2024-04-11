export interface ICbComplaintField {
  id: number;
  complaintFieldName?: string | null;
}

export type NewCbComplaintField = Omit<ICbComplaintField, 'id'> & { id: null };
