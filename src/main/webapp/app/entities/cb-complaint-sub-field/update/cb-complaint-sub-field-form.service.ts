import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbComplaintSubField, NewCbComplaintSubField } from '../cb-complaint-sub-field.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbComplaintSubField for edit and NewCbComplaintSubFieldFormGroupInput for create.
 */
type CbComplaintSubFieldFormGroupInput = ICbComplaintSubField | PartialWithRequiredKeyOf<NewCbComplaintSubField>;

type CbComplaintSubFieldFormDefaults = Pick<NewCbComplaintSubField, 'id'>;

type CbComplaintSubFieldFormGroupContent = {
  id: FormControl<ICbComplaintSubField['id'] | NewCbComplaintSubField['id']>;
  complaintSubFieldName: FormControl<ICbComplaintSubField['complaintSubFieldName']>;
};

export type CbComplaintSubFieldFormGroup = FormGroup<CbComplaintSubFieldFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbComplaintSubFieldFormService {
  createCbComplaintSubFieldFormGroup(cbComplaintSubField: CbComplaintSubFieldFormGroupInput = { id: null }): CbComplaintSubFieldFormGroup {
    const cbComplaintSubFieldRawValue = {
      ...this.getFormDefaults(),
      ...cbComplaintSubField,
    };
    return new FormGroup<CbComplaintSubFieldFormGroupContent>({
      id: new FormControl(
        { value: cbComplaintSubFieldRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      complaintSubFieldName: new FormControl(cbComplaintSubFieldRawValue.complaintSubFieldName),
    });
  }

  getCbComplaintSubField(form: CbComplaintSubFieldFormGroup): ICbComplaintSubField | NewCbComplaintSubField {
    return form.getRawValue() as ICbComplaintSubField | NewCbComplaintSubField;
  }

  resetForm(form: CbComplaintSubFieldFormGroup, cbComplaintSubField: CbComplaintSubFieldFormGroupInput): void {
    const cbComplaintSubFieldRawValue = { ...this.getFormDefaults(), ...cbComplaintSubField };
    form.reset(
      {
        ...cbComplaintSubFieldRawValue,
        id: { value: cbComplaintSubFieldRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CbComplaintSubFieldFormDefaults {
    return {
      id: null,
    };
  }
}
