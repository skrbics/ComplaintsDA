import { ICbCity } from 'app/entities/cb-city/cb-city.model';

export interface ICbLocation {
  id: number;
  locationName?: string | null;
  cbCity?: Pick<ICbCity, 'id'> | null;
}

export type NewCbLocation = Omit<ICbLocation, 'id'> & { id: null };
