import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICbComplaintField } from '../cb-complaint-field.model';

@Component({
  standalone: true,
  selector: 'jhi-cb-complaint-field-detail',
  templateUrl: './cb-complaint-field-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CbComplaintFieldDetailComponent {
  cbComplaintField = input<ICbComplaintField | null>(null);

  previousState(): void {
    window.history.back();
  }
}
