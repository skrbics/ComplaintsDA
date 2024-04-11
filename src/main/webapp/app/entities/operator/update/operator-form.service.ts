import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOperator, NewOperator } from '../operator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOperator for edit and NewOperatorFormGroupInput for create.
 */
type OperatorFormGroupInput = IOperator | PartialWithRequiredKeyOf<NewOperator>;

type OperatorFormDefaults = Pick<NewOperator, 'id'>;

type OperatorFormGroupContent = {
  id: FormControl<IOperator['id'] | NewOperator['id']>;
  firstName: FormControl<IOperator['firstName']>;
  middleName: FormControl<IOperator['middleName']>;
  lastName: FormControl<IOperator['lastName']>;
  email: FormControl<IOperator['email']>;
  phone: FormControl<IOperator['phone']>;
};

export type OperatorFormGroup = FormGroup<OperatorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OperatorFormService {
  createOperatorFormGroup(operator: OperatorFormGroupInput = { id: null }): OperatorFormGroup {
    const operatorRawValue = {
      ...this.getFormDefaults(),
      ...operator,
    };
    return new FormGroup<OperatorFormGroupContent>({
      id: new FormControl(
        { value: operatorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      firstName: new FormControl(operatorRawValue.firstName),
      middleName: new FormControl(operatorRawValue.middleName),
      lastName: new FormControl(operatorRawValue.lastName),
      email: new FormControl(operatorRawValue.email),
      phone: new FormControl(operatorRawValue.phone),
    });
  }

  getOperator(form: OperatorFormGroup): IOperator | NewOperator {
    return form.getRawValue() as IOperator | NewOperator;
  }

  resetForm(form: OperatorFormGroup, operator: OperatorFormGroupInput): void {
    const operatorRawValue = { ...this.getFormDefaults(), ...operator };
    form.reset(
      {
        ...operatorRawValue,
        id: { value: operatorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OperatorFormDefaults {
    return {
      id: null,
    };
  }
}
