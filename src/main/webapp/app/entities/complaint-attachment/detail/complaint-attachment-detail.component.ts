import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IComplaintAttachment } from '../complaint-attachment.model';

@Component({
  standalone: true,
  selector: 'jhi-complaint-attachment-detail',
  templateUrl: './complaint-attachment-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ComplaintAttachmentDetailComponent {
  complaintAttachment = input<IComplaintAttachment | null>(null);

  previousState(): void {
    window.history.back();
  }
}
