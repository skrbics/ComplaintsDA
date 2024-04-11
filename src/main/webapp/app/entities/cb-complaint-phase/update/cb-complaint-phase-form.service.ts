import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICbComplaintPhase, NewCbComplaintPhase } from '../cb-complaint-phase.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICbComplaintPhase for edit and NewCbComplaintPhaseFormGroupInput for create.
 */
type CbComplaintPhaseFormGroupInput = ICbComplaintPhase | PartialWithRequiredKeyOf<NewCbComplaintPhase>;

type CbComplaintPhaseFormDefaults = Pick<NewCbComplaintPhase, 'id'>;

type CbComplaintPhaseFormGroupContent = {
  id: FormControl<ICbComplaintPhase['id'] | NewCbComplaintPhase['id']>;
  ordinalNo: FormControl<ICbComplaintPhase['ordinalNo']>;
  complaintPhaseName: FormControl<ICbComplaintPhase['complaintPhaseName']>;
};

export type CbComplaintPhaseFormGroup = FormGroup<CbComplaintPhaseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CbComplaintPhaseFormService {
  createCbComplaintPhaseFormGroup(cbComplaintPhase: CbComplaintPhaseFormGroupInput = { id: null }): CbComplaintPhaseFormGroup {
    const cbComplaintPhaseRawValue = {
      ...this.getFormDefaults(),
      ...cbComplaintPhase,
    };
    return new FormGroup<CbComplaintPhaseFormGroupContent>({
      id: new FormControl(
        { value: cbComplaintPhaseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ordinalNo: new FormControl(cbComplaintPhaseRawValue.ordinalNo),
      complaintPhaseName: new FormControl(cbComplaintPhaseRawValue.complaintPhaseName),
    });
  }

  getCbComplaintPhase(form: CbComplaintPhaseFormGroup): ICbComplaintPhase | NewCbComplaintPhase {
    return form.getRawValue() as ICbComplaintPhase | NewCbComplaintPhase;
  }

  resetForm(form: CbComplaintPhaseFormGroup, cbComplaintPhase: CbComplaintPhaseFormGroupInput): void {
    const cbComplaintPhaseRawValue = { ...this.getFormDefaults(), ...cbComplaintPhase };
    form.reset(
      {
        ...cbComplaintPhaseRawValue,
        id: { value: cbComplaintPhaseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CbComplaintPhaseFormDefaults {
    return {
      id: null,
    };
  }
}
