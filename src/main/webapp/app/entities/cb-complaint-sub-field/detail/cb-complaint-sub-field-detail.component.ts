import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICbComplaintSubField } from '../cb-complaint-sub-field.model';

@Component({
  standalone: true,
  selector: 'jhi-cb-complaint-sub-field-detail',
  templateUrl: './cb-complaint-sub-field-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CbComplaintSubFieldDetailComponent {
  cbComplaintSubField = input<ICbComplaintSubField | null>(null);

  previousState(): void {
    window.history.back();
  }
}
