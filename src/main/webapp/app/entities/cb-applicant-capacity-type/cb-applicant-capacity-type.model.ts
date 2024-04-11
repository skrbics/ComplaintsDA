export interface ICbApplicantCapacityType {
  id: number;
  applicantCapacityTypeName?: string | null;
}

export type NewCbApplicantCapacityType = Omit<ICbApplicantCapacityType, 'id'> & { id: null };
