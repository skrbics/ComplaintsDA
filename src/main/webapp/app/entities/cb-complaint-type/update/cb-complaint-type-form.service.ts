import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbComplaintType, NewCbComplaintType } from '../cb-complaint-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbComplaintType for edit and NewCbComplaintTypeFormGroupInput for create.
 */
type CbComplaintTypeFormGroupInput = ICbComplaintType | PartialWithRequiredKeyOf<NewCbComplaintType>;

type CbComplaintTypeFormDefaults = Pick<NewCbComplaintType, 'id'>;

type CbComplaintTypeFormGroupContent = {
  id: FormControl<ICbComplaintType['id'] | NewCbComplaintType['id']>;
  complaintTypeName: FormControl<ICbComplaintType['complaintTypeName']>;
};

export type CbComplaintTypeFormGroup = FormGroup<CbComplaintTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbComplaintTypeFormService {
  createCbComplaintTypeFormGroup(cbComplaintType: CbComplaintTypeFormGroupInput = { id: null }): CbComplaintTypeFormGroup {
    const cbComplaintTypeRawValue = {
      ...this.getFormDefaults(),
      ...cbComplaintType,
    };
    return new FormGroup<CbComplaintTypeFormGroupContent>({
      id: new FormControl(
        { value: cbComplaintTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      complaintTypeName: new FormControl(cbComplaintTypeRawValue.complaintTypeName),
    });
  }

  getCbComplaintType(form: CbComplaintTypeFormGroup): ICbComplaintType | NewCbComplaintType {
    return form.getRawValue() as ICbComplaintType | NewCbComplaintType;
  }

  resetForm(form: CbComplaintTypeFormGroup, cbComplaintType: CbComplaintTypeFormGroupInput): void {
    const cbComplaintTypeRawValue = { ...this.getFormDefaults(), ...cbComplaintType };
    form.reset(
      {
        ...cbComplaintTypeRawValue,
        id: { value: cbComplaintTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CbComplaintTypeFormDefaults {
    return {
      id: null,
    };
  }
}
