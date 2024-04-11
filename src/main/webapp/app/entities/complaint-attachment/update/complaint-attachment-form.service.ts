import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IComplaintAttachment, NewComplaintAttachment } from '../complaint-attachment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IComplaintAttachment for edit and NewComplaintAttachmentFormGroupInput for create.
 */
type ComplaintAttachmentFormGroupInput = IComplaintAttachment | PartialWithRequiredKeyOf<NewComplaintAttachment>;

type ComplaintAttachmentFormDefaults = Pick<NewComplaintAttachment, 'id'>;

type ComplaintAttachmentFormGroupContent = {
  id: FormControl<IComplaintAttachment['id'] | NewComplaintAttachment['id']>;
  ordinalNo: FormControl<IComplaintAttachment['ordinalNo']>;
  name: FormControl<IComplaintAttachment['name']>;
  path: FormControl<IComplaintAttachment['path']>;
  complaint: FormControl<IComplaintAttachment['complaint']>;
  cbAttachmentType: FormControl<IComplaintAttachment['cbAttachmentType']>;
};

export type ComplaintAttachmentFormGroup = FormGroup<ComplaintAttachmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ComplaintAttachmentFormService {
  createComplaintAttachmentFormGroup(complaintAttachment: ComplaintAttachmentFormGroupInput = { id: null }): ComplaintAttachmentFormGroup {
    const complaintAttachmentRawValue = {
      ...this.getFormDefaults(),
      ...complaintAttachment,
    };
    return new FormGroup<ComplaintAttachmentFormGroupContent>({
      id: new FormControl(
        { value: complaintAttachmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ordinalNo: new FormControl(complaintAttachmentRawValue.ordinalNo),
      name: new FormControl(complaintAttachmentRawValue.name),
      path: new FormControl(complaintAttachmentRawValue.path),
      complaint: new FormControl(complaintAttachmentRawValue.complaint),
      cbAttachmentType: new FormControl(complaintAttachmentRawValue.cbAttachmentType),
    });
  }

  getComplaintAttachment(form: ComplaintAttachmentFormGroup): IComplaintAttachment | NewComplaintAttachment {
    return form.getRawValue() as IComplaintAttachment | NewComplaintAttachment;
  }

  resetForm(form: ComplaintAttachmentFormGroup, complaintAttachment: ComplaintAttachmentFormGroupInput): void {
    const complaintAttachmentRawValue = { ...this.getFormDefaults(), ...complaintAttachment };
    form.reset(
      {
        ...complaintAttachmentRawValue,
        id: { value: complaintAttachmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ComplaintAttachmentFormDefaults {
    return {
      id: null,
    };
  }
}
