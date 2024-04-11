import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbLocation, NewCbLocation } from '../cb-location.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbLocation for edit and NewCbLocationFormGroupInput for create.
 */
type CbLocationFormGroupInput = ICbLocation | PartialWithRequiredKeyOf<NewCbLocation>;

type CbLocationFormDefaults = Pick<NewCbLocation, 'id'>;

type CbLocationFormGroupContent = {
  id: FormControl<ICbLocation['id'] | NewCbLocation['id']>;
  locationName: FormControl<ICbLocation['locationName']>;
  cbCity: FormControl<ICbLocation['cbCity']>;
};

export type CbLocationFormGroup = FormGroup<CbLocationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbLocationFormService {
  createCbLocationFormGroup(cbLocation: CbLocationFormGroupInput = { id: null }): CbLocationFormGroup {
    const cbLocationRawValue = {
      ...this.getFormDefaults(),
      ...cbLocation,
    };
    return new FormGroup<CbLocationFormGroupContent>({
      id: new FormControl(
        { value: cbLocationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      locationName: new FormControl(cbLocationRawValue.locationName),
      cbCity: new FormControl(cbLocationRawValue.cbCity),
    });
  }

  getCbLocation(form: CbLocationFormGroup): ICbLocation | NewCbLocation {
    return form.getRawValue() as ICbLocation | NewCbLocation;
  }

  resetForm(form: CbLocationFormGroup, cbLocation: CbLocationFormGroupInput): void {
    const cbLocationRawValue = { ...this.getFormDefaults(), ...cbLocation };
    form.reset(
      {
        ...cbLocationRawValue,
        id: { value: cbLocationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CbLocationFormDefaults {
    return {
      id: null,
    };
  }
}
