import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICbLocation } from '../cb-location.model';

@Component({
  standalone: true,
  selector: 'jhi-cb-location-detail',
  templateUrl: './cb-location-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CbLocationDetailComponent {
  cbLocation = input<ICbLocation | null>(null);

  previousState(): void {
    window.history.back();
  }
}
