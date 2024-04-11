import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbComplaintField, NewCbComplaintField } from '../cb-complaint-field.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbComplaintField for edit and NewCbComplaintFieldFormGroupInput for create.
 */
type CbComplaintFieldFormGroupInput = ICbComplaintField | PartialWithRequiredKeyOf<NewCbComplaintField>;

type CbComplaintFieldFormDefaults = Pick<NewCbComplaintField, 'id'>;

type CbComplaintFieldFormGroupContent = {
  id: FormControl<ICbComplaintField['id'] | NewCbComplaintField['id']>;
  complaintFieldName: FormControl<ICbComplaintField['complaintFieldName']>;
};

export type CbComplaintFieldFormGroup = FormGroup<CbComplaintFieldFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbComplaintFieldFormService {
  createCbComplaintFieldFormGroup(cbComplaintField: CbComplaintFieldFormGroupInput = { id: null }): CbComplaintFieldFormGroup {
    const cbComplaintFieldRawValue = {
      ...this.getFormDefaults(),
      ...cbComplaintField,
    };
    return new FormGroup<CbComplaintFieldFormGroupContent>({
      id: new FormControl(
        { value: cbComplaintFieldRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      complaintFieldName: new FormControl(cbComplaintFieldRawValue.complaintFieldName),
    });
  }

  getCbComplaintField(form: CbComplaintFieldFormGroup): ICbComplaintField | NewCbComplaintField {
    return form.getRawValue() as ICbComplaintField | NewCbComplaintField;
  }

  resetForm(form: CbComplaintFieldFormGroup, cbComplaintField: CbComplaintFieldFormGroupInput): void {
    const cbComplaintFieldRawValue = { ...this.getFormDefaults(), ...cbComplaintField };
    form.reset(
      {
        ...cbComplaintFieldRawValue,
        id: { value: cbComplaintFieldRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CbComplaintFieldFormDefaults {
    return {
      id: null,
    };
  }
}
