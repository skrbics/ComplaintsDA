import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICbComplaintSubField } from '../cb-complaint-sub-field.model';
import { CbComplaintSubFieldService } from '../service/cb-complaint-sub-field.service';

@Component({
  standalone: true,
  templateUrl: './cb-complaint-sub-field-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CbComplaintSubFieldDeleteDialogComponent {
  cbComplaintSubField?: ICbComplaintSubField;

  protected cbComplaintSubFieldService = inject(CbComplaintSubFieldService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbComplaintSubFieldService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
