import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICbComplaintChannel } from '../cb-complaint-channel.model';
import { CbComplaintChannelService } from '../service/cb-complaint-channel.service';

@Component({
  standalone: true,
  templateUrl: './cb-complaint-channel-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CbComplaintChannelDeleteDialogComponent {
  cbComplaintChannel?: ICbComplaintChannel;

  protected cbComplaintChannelService = inject(CbComplaintChannelService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbComplaintChannelService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
