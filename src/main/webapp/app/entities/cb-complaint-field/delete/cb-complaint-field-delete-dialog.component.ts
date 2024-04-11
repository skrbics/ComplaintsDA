import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICbComplaintField } from '../cb-complaint-field.model';
import { CbComplaintFieldService } from '../service/cb-complaint-field.service';

@Component({
  standalone: true,
  templateUrl: './cb-complaint-field-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CbComplaintFieldDeleteDialogComponent {
  cbComplaintField?: ICbComplaintField;

  protected cbComplaintFieldService = inject(CbComplaintFieldService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbComplaintFieldService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
