import { ICbCity } from 'app/entities/cb-city/cb-city.model';
import { ICbCountry } from 'app/entities/cb-country/cb-country.model';

export interface IAddress {
  id: number;
  street?: string | null;
  houseNo?: string | null;
  cbCity?: Pick<ICbCity, 'id'> | null;
  cbCountry?: Pick<ICbCountry, 'id'> | null;
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };
