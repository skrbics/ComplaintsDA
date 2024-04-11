import { IAddress } from 'app/entities/address/address.model';

export interface IApplicant {
  id: number;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phone?: string | null;
  address?: Pick<IAddress, 'id'> | null;
}

export type NewApplicant = Omit<IApplicant, 'id'> & { id: null };
