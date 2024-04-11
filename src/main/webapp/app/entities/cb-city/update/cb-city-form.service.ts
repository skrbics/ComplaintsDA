import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbCity, NewCbCity } from '../cb-city.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbCity for edit and NewCbCityFormGroupInput for create.
 */
type CbCityFormGroupInput = ICbCity | PartialWithRequiredKeyOf<NewCbCity>;

type CbCityFormDefaults = Pick<NewCbCity, 'id'>;

type CbCityFormGroupContent = {
  id: FormControl<ICbCity['id'] | NewCbCity['id']>;
  name: FormControl<ICbCity['name']>;
  zip: FormControl<ICbCity['zip']>;
};

export type CbCityFormGroup = FormGroup<CbCityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbCityFormService {
  createCbCityFormGroup(cbCity: CbCityFormGroupInput = { id: null }): CbCityFormGroup {
    const cbCityRawValue = {
      ...this.getFormDefaults(),
      ...cbCity,
    };
    return new FormGroup<CbCityFormGroupContent>({
      id: new FormControl(
        { value: cbCityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(cbCityRawValue.name),
      zip: new FormControl(cbCityRawValue.zip),
    });
  }

  getCbCity(form: CbCityFormGroup): ICbCity | NewCbCity {
    return form.getRawValue() as ICbCity | NewCbCity;
  }

  resetForm(form: CbCityFormGroup, cbCity: CbCityFormGroupInput): void {
    const cbCityRawValue = { ...this.getFormDefaults(), ...cbCity };
    form.reset(
      {
        ...cbCityRawValue,
        id: { value: cbCityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CbCityFormDefaults {
    return {
      id: null,
    };
  }
}
