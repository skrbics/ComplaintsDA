import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICbAttachmentType } from '../cb-attachment-type.model';

@Component({
  standalone: true,
  selector: 'jhi-cb-attachment-type-detail',
  templateUrl: './cb-attachment-type-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CbAttachmentTypeDetailComponent {
  cbAttachmentType = input<ICbAttachmentType | null>(null);

  previousState(): void {
    window.history.back();
  }
}
