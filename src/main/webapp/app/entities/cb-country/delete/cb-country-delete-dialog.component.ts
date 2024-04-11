import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICbCountry } from '../cb-country.model';
import { CbCountryService } from '../service/cb-country.service';

@Component({
  standalone: true,
  templateUrl: './cb-country-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CbCountryDeleteDialogComponent {
  cbCountry?: ICbCountry;

  protected cbCountryService = inject(CbCountryService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbCountryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
