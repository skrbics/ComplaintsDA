import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICbLocation } from '../cb-location.model';
import { CbLocationService } from '../service/cb-location.service';

@Component({
  standalone: true,
  templateUrl: './cb-location-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CbLocationDeleteDialogComponent {
  cbLocation?: ICbLocation;

  protected cbLocationService = inject(CbLocationService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbLocationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
