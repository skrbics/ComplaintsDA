import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICbCity } from '../cb-city.model';
import { CbCityService } from '../service/cb-city.service';

@Component({
  standalone: true,
  templateUrl: './cb-city-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CbCityDeleteDialogComponent {
  cbCity?: ICbCity;

  protected cbCityService = inject(CbCityService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbCityService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
