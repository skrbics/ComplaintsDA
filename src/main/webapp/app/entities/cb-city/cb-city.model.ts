export interface ICbCity {
  id: number;
  name?: string | null;
  zip?: string | null;
}

export type NewCbCity = Omit<ICbCity, 'id'> & { id: null };
