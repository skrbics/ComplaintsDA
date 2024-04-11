export interface ICbComplaintType {
  id: number;
  complaintTypeName?: string | null;
}

export type NewCbComplaintType = Omit<ICbComplaintType, 'id'> & { id: null };
