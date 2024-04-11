import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICbComplaintPhase } from '../cb-complaint-phase.model';
import { CbComplaintPhaseService } from '../service/cb-complaint-phase.service';

@Component({
  standalone: true,
  templateUrl: './cb-complaint-phase-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CbComplaintPhaseDeleteDialogComponent {
  cbComplaintPhase?: ICbComplaintPhase;

  protected cbComplaintPhaseService = inject(CbComplaintPhaseService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbComplaintPhaseService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
