import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICbApplicantCapacityType } from '../cb-applicant-capacity-type.model';
import { CbApplicantCapacityTypeService } from '../service/cb-applicant-capacity-type.service';

@Component({
  standalone: true,
  templateUrl: './cb-applicant-capacity-type-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CbApplicantCapacityTypeDeleteDialogComponent {
  cbApplicantCapacityType?: ICbApplicantCapacityType;

  protected cbApplicantCapacityTypeService = inject(CbApplicantCapacityTypeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cbApplicantCapacityTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
