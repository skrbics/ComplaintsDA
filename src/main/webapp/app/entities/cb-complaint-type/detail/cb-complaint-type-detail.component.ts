import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICbComplaintType } from '../cb-complaint-type.model';

@Component({
  standalone: true,
  selector: 'jhi-cb-complaint-type-detail',
  templateUrl: './cb-complaint-type-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CbComplaintTypeDetailComponent {
  cbComplaintType = input<ICbComplaintType | null>(null);

  previousState(): void {
    window.history.back();
  }
}
