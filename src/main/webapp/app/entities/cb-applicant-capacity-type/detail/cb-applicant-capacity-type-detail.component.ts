import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICbApplicantCapacityType } from '../cb-applicant-capacity-type.model';

@Component({
  standalone: true,
  selector: 'jhi-cb-applicant-capacity-type-detail',
  templateUrl: './cb-applicant-capacity-type-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CbApplicantCapacityTypeDetailComponent {
  cbApplicantCapacityType = input<ICbApplicantCapacityType | null>(null);

  previousState(): void {
    window.history.back();
  }
}
