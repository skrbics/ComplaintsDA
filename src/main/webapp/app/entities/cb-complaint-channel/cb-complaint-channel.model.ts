export interface ICbComplaintChannel {
  id: number;
  complaintChannelName?: string | null;
}

export type NewCbComplaintChannel = Omit<ICbComplaintChannel, 'id'> & { id: null };
