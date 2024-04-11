import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICbAttachmentType } from '../cb-attachment-type.model';
import { CbAttachmentTypeService } from '../service/cb-attachment-type.service';

@Component({
  standalone: true,
  templateUrl: './cb-attachment-type-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CbAttachmentTypeDeleteDialogComponent {
  cbAttachmentType?: ICbAttachmentType;

  protected cbAttachmentTypeService = inject(CbAttachmentTypeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbAttachmentTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
