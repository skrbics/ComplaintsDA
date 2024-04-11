export interface IOperator {
  id: number;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phone?: string | null;
}

export type NewOperator = Omit<IOperator, 'id'> & { id: null };
