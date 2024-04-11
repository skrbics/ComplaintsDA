import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IComplaintAttachment } from '../complaint-attachment.model';
import { ComplaintAttachmentService } from '../service/complaint-attachment.service';

@Component({
  standalone: true,
  templateUrl: './complaint-attachment-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ComplaintAttachmentDeleteDialogComponent {
  complaintAttachment?: IComplaintAttachment;

  protected complaintAttachmentService = inject(ComplaintAttachmentService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.complaintAttachmentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
