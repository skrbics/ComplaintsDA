import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICbComplaintPhase } from '../cb-complaint-phase.model';

@Component({
  standalone: true,
  selector: 'jhi-cb-complaint-phase-detail',
  templateUrl: './cb-complaint-phase-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CbComplaintPhaseDetailComponent {
  cbComplaintPhase = input<ICbComplaintPhase | null>(null);

  previousState(): void {
    window.history.back();
  }
}
