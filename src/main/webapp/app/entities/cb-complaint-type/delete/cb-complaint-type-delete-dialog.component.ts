import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICbComplaintType } from '../cb-complaint-type.model';
import { CbComplaintTypeService } from '../service/cb-complaint-type.service';

@Component({
  standalone: true,
  templateUrl: './cb-complaint-type-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CbComplaintTypeDeleteDialogComponent {
  cbComplaintType?: ICbComplaintType;

  protected cbComplaintTypeService = inject(CbComplaintTypeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbComplaintTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
