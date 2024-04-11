import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbApplicantCapacityType, NewCbApplicantCapacityType } from '../cb-applicant-capacity-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbApplicantCapacityType for edit and NewCbApplicantCapacityTypeFormGroupInput for create.
 */
type CbApplicantCapacityTypeFormGroupInput = ICbApplicantCapacityType | PartialWithRequiredKeyOf<NewCbApplicantCapacityType>;

type CbApplicantCapacityTypeFormDefaults = Pick<NewCbApplicantCapacityType, 'id'>;

type CbApplicantCapacityTypeFormGroupContent = {
  id: FormControl<ICbApplicantCapacityType['id'] | NewCbApplicantCapacityType['id']>;
  applicantCapacityTypeName: FormControl<ICbApplicantCapacityType['applicantCapacityTypeName']>;
};

export type CbApplicantCapacityTypeFormGroup = FormGroup<CbApplicantCapacityTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbApplicantCapacityTypeFormService {
  createCbApplicantCapacityTypeFormGroup(
    cbApplicantCapacityType: CbApplicantCapacityTypeFormGroupInput = { id: null },
  ): CbApplicantCapacityTypeFormGroup {
    const cbApplicantCapacityTypeRawValue = {
      ...this.getFormDefaults(),
      ...cbApplicantCapacityType,
    };
    return new FormGroup<CbApplicantCapacityTypeFormGroupContent>({
      id: new FormControl(
        { value: cbApplicantCapacityTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      applicantCapacityTypeName: new FormControl(cbApplicantCapacityTypeRawValue.applicantCapacityTypeName),
    });
  }

  getCbApplicantCapacityType(form: CbApplicantCapacityTypeFormGroup): ICbApplicantCapacityType | NewCbApplicantCapacityType {
    return form.getRawValue() as ICbApplicantCapacityType | NewCbApplicantCapacityType;
  }

  resetForm(form: CbApplicantCapacityTypeFormGroup, cbApplicantCapacityType: CbApplicantCapacityTypeFormGroupInput): void {
    const cbApplicantCapacityTypeRawValue = { ...this.getFormDefaults(), ...cbApplicantCapacityType };
    form.reset(
      {
        ...cbApplicantCapacityTypeRawValue,
        id: { value: cbApplicantCapacityTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CbApplicantCapacityTypeFormDefaults {
    return {
      id: null,
    };
  }
}
