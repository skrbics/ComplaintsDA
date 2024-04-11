import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IComplaint, NewComplaint } from '../complaint.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IComplaint for edit and NewComplaintFormGroupInput for create.
 */
type ComplaintFormGroupInput = IComplaint | PartialWithRequiredKeyOf<NewComplaint>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IComplaint | NewComplaint> = Omit<T, 'dateAndTime'> & {
  dateAndTime?: string | null;
};

type ComplaintFormRawValue = FormValueOf<IComplaint>;

type NewComplaintFormRawValue = FormValueOf<NewComplaint>;

type ComplaintFormDefaults = Pick<NewComplaint, 'id' | 'dateAndTime' | 'writtenReplyBySMS'>;

type ComplaintFormGroupContent = {
  id: FormControl<ComplaintFormRawValue['id'] | NewComplaint['id']>;
  complaintText: FormControl<ComplaintFormRawValue['complaintText']>;
  dateAndTime: FormControl<ComplaintFormRawValue['dateAndTime']>;
  writtenReplyBySMS: FormControl<ComplaintFormRawValue['writtenReplyBySMS']>;
  applicant: FormControl<ComplaintFormRawValue['applicant']>;
  cbComplaintField: FormControl<ComplaintFormRawValue['cbComplaintField']>;
  cbComplaintSubField: FormControl<ComplaintFormRawValue['cbComplaintSubField']>;
  cbComplaintType: FormControl<ComplaintFormRawValue['cbComplaintType']>;
  cbComplaintChannel: FormControl<ComplaintFormRawValue['cbComplaintChannel']>;
  cbApplicantCapacityType: FormControl<ComplaintFormRawValue['cbApplicantCapacityType']>;
  cbComplaintPhase: FormControl<ComplaintFormRawValue['cbComplaintPhase']>;
};

export type ComplaintFormGroup = FormGroup<ComplaintFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ComplaintFormService {
  createComplaintFormGroup(complaint: ComplaintFormGroupInput = { id: null }): ComplaintFormGroup {
    const complaintRawValue = this.convertComplaintToComplaintRawValue({
      ...this.getFormDefaults(),
      ...complaint,
    });
    return new FormGroup<ComplaintFormGroupContent>({
      id: new FormControl(
        { value: complaintRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      complaintText: new FormControl(complaintRawValue.complaintText),
      dateAndTime: new FormControl(complaintRawValue.dateAndTime),
      writtenReplyBySMS: new FormControl(complaintRawValue.writtenReplyBySMS),
      applicant: new FormControl(complaintRawValue.applicant),
      cbComplaintField: new FormControl(complaintRawValue.cbComplaintField),
      cbComplaintSubField: new FormControl(complaintRawValue.cbComplaintSubField),
      cbComplaintType: new FormControl(complaintRawValue.cbComplaintType),
      cbComplaintChannel: new FormControl(complaintRawValue.cbComplaintChannel),
      cbApplicantCapacityType: new FormControl(complaintRawValue.cbApplicantCapacityType),
      cbComplaintPhase: new FormControl(complaintRawValue.cbComplaintPhase),
    });
  }

  getComplaint(form: ComplaintFormGroup): IComplaint | NewComplaint {
    return this.convertComplaintRawValueToComplaint(form.getRawValue() as ComplaintFormRawValue | NewComplaintFormRawValue);
  }

  resetForm(form: ComplaintFormGroup, complaint: ComplaintFormGroupInput): void {
    const complaintRawValue = this.convertComplaintToComplaintRawValue({ ...this.getFormDefaults(), ...complaint });
    form.reset(
      {
        ...complaintRawValue,
        id: { value: complaintRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ComplaintFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateAndTime: currentTime,
      writtenReplyBySMS: false,
    };
  }

  private convertComplaintRawValueToComplaint(rawComplaint: ComplaintFormRawValue | NewComplaintFormRawValue): IComplaint | NewComplaint {
    return {
      ...rawComplaint,
      dateAndTime: dayjs(rawComplaint.dateAndTime, DATE_TIME_FORMAT),
    };
  }

  private convertComplaintToComplaintRawValue(
    complaint: IComplaint | (Partial<NewComplaint> & ComplaintFormDefaults),
  ): ComplaintFormRawValue | PartialWithRequiredKeyOf<NewComplaintFormRawValue> {
    return {
      ...complaint,
      dateAndTime: complaint.dateAndTime ? complaint.dateAndTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
