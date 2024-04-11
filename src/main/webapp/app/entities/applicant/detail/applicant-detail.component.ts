import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IApplicant } from '../applicant.model';

@Component({
  standalone: true,
  selector: 'jhi-applicant-detail',
  templateUrl: './applicant-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ApplicantDetailComponent {
  applicant = input<IApplicant | null>(null);

  previousState(): void {
    window.history.back();
  }
}
