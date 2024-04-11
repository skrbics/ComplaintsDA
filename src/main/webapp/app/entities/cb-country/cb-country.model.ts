export interface ICbCountry {
  id: number;
  name?: string | null;
  abbreviation?: string | null;
}

export type NewCbCountry = Omit<ICbCountry, 'id'> & { id: null };
