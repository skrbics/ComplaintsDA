import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbAttachmentType, NewCbAttachmentType } from '../cb-attachment-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbAttachmentType for edit and NewCbAttachmentTypeFormGroupInput for create.
 */
type CbAttachmentTypeFormGroupInput = ICbAttachmentType | PartialWithRequiredKeyOf<NewCbAttachmentType>;

type CbAttachmentTypeFormDefaults = Pick<NewCbAttachmentType, 'id'>;

type CbAttachmentTypeFormGroupContent = {
  id: FormControl<ICbAttachmentType['id'] | NewCbAttachmentType['id']>;
  name: FormControl<ICbAttachmentType['name']>;
  extension: FormControl<ICbAttachmentType['extension']>;
};

export type CbAttachmentTypeFormGroup = FormGroup<CbAttachmentTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbAttachmentTypeFormService {
  createCbAttachmentTypeFormGroup(cbAttachmentType: CbAttachmentTypeFormGroupInput = { id: null }): CbAttachmentTypeFormGroup {
    const cbAttachmentTypeRawValue = {
      ...this.getFormDefaults(),
      ...cbAttachmentType,
    };
    return new FormGroup<CbAttachmentTypeFormGroupContent>({
      id: new FormControl(
        { value: cbAttachmentTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(cbAttachmentTypeRawValue.name),
      extension: new FormControl(cbAttachmentTypeRawValue.extension),
    });
  }

  getCbAttachmentType(form: CbAttachmentTypeFormGroup): ICbAttachmentType | NewCbAttachmentType {
    return form.getRawValue() as ICbAttachmentType | NewCbAttachmentType;
  }

  resetForm(form: CbAttachmentTypeFormGroup, cbAttachmentType: CbAttachmentTypeFormGroupInput): void {
    const cbAttachmentTypeRawValue = { ...this.getFormDefaults(), ...cbAttachmentType };
    form.reset(
      {
        ...cbAttachmentTypeRawValue,
        id: { value: cbAttachmentTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CbAttachmentTypeFormDefaults {
    return {
      id: null,
    };
  }
}
