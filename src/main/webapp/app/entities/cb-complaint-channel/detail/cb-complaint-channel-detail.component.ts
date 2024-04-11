import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICbComplaintChannel } from '../cb-complaint-channel.model';

@Component({
  standalone: true,
  selector: 'jhi-cb-complaint-channel-detail',
  templateUrl: './cb-complaint-channel-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CbComplaintChannelDetailComponent {
  cbComplaintChannel = input<ICbComplaintChannel | null>(null);

  previousState(): void {
    window.history.back();
  }
}
