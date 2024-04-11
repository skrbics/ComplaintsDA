import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IComplaint } from '../complaint.model';

@Component({
  standalone: true,
  selector: 'jhi-complaint-detail',
  templateUrl: './complaint-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ComplaintDetailComponent {
  complaint = input<IComplaint | null>(null);

  previousState(): void {
    window.history.back();
  }
}
