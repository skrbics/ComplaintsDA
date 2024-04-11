import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbComplaintChannel, NewCbComplaintChannel } from '../cb-complaint-channel.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbComplaintChannel for edit and NewCbComplaintChannelFormGroupInput for create.
 */
type CbComplaintChannelFormGroupInput = ICbComplaintChannel | PartialWithRequiredKeyOf<NewCbComplaintChannel>;

type CbComplaintChannelFormDefaults = Pick<NewCbComplaintChannel, 'id'>;

type CbComplaintChannelFormGroupContent = {
  id: FormControl<ICbComplaintChannel['id'] | NewCbComplaintChannel['id']>;
  complaintChannelName: FormControl<ICbComplaintChannel['complaintChannelName']>;
};

export type CbComplaintChannelFormGroup = FormGroup<CbComplaintChannelFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbComplaintChannelFormService {
  createCbComplaintChannelFormGroup(cbComplaintChannel: CbComplaintChannelFormGroupInput = { id: null }): CbComplaintChannelFormGroup {
    const cbComplaintChannelRawValue = {
      ...this.getFormDefaults(),
      ...cbComplaintChannel,
    };
    return new FormGroup<CbComplaintChannelFormGroupContent>({
      id: new FormControl(
        { value: cbComplaintChannelRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      complaintChannelName: new FormControl(cbComplaintChannelRawValue.complaintChannelName),
    });
  }

  getCbComplaintChannel(form: CbComplaintChannelFormGroup): ICbComplaintChannel | NewCbComplaintChannel {
    return form.getRawValue() as ICbComplaintChannel | NewCbComplaintChannel;
  }

  resetForm(form: CbComplaintChannelFormGroup, cbComplaintChannel: CbComplaintChannelFormGroupInput): void {
    const cbComplaintChannelRawValue = { ...this.getFormDefaults(), ...cbComplaintChannel };
    form.reset(
      {
        ...cbComplaintChannelRawValue,
        id: { value: cbComplaintChannelRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CbComplaintChannelFormDefaults {
    return {
      id: null,
    };
  }
}
