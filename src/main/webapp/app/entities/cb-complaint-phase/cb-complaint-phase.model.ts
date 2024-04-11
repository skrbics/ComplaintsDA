export interface ICbComplaintPhase {
  id: number;
  ordinalNo?: number | null;
  complaintPhaseName?: string | null;
}

export type NewCbComplaintPhase = Omit<ICbComplaintPhase, 'id'> & { id: null };
