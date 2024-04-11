import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbCountry, NewCbCountry } from '../cb-country.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbCountry for edit and NewCbCountryFormGroupInput for create.
 */
type CbCountryFormGroupInput = ICbCountry | PartialWithRequiredKeyOf<NewCbCountry>;

type CbCountryFormDefaults = Pick<NewCbCountry, 'id'>;

type CbCountryFormGroupContent = {
  id: FormControl<ICbCountry['id'] | NewCbCountry['id']>;
  name: FormControl<ICbCountry['name']>;
  abbreviation: FormControl<ICbCountry['abbreviation']>;
};

export type CbCountryFormGroup = FormGroup<CbCountryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbCountryFormService {
  createCbCountryFormGroup(cbCountry: CbCountryFormGroupInput = { id: null }): CbCountryFormGroup {
    const cbCountryRawValue = {
      ...this.getFormDefaults(),
      ...cbCountry,
    };
    return new FormGroup<CbCountryFormGroupContent>({
      id: new FormControl(
        { value: cbCountryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(cbCountryRawValue.name),
      abbreviation: new FormControl(cbCountryRawValue.abbreviation),
    });
  }

  getCbCountry(form: CbCountryFormGroup): ICbCountry | NewCbCountry {
    return form.getRawValue() as ICbCountry | NewCbCountry;
  }

  resetForm(form: CbCountryFormGroup, cbCountry: CbCountryFormGroupInput): void {
    const cbCountryRawValue = { ...this.getFormDefaults(), ...cbCountry };
    form.reset(
      {
        ...cbCountryRawValue,
        id: { value: cbCountryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CbCountryFormDefaults {
    return {
      id: null,
    };
  }
}
