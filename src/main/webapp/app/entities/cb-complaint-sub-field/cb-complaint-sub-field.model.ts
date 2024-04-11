export interface ICbComplaintSubField {
  id: number;
  complaintSubFieldName?: string | null;
}

export type NewCbComplaintSubField = Omit<ICbComplaintSubField, 'id'> & { id: null };
