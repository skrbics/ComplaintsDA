import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbComplaintType } from '../cb-complaint-type.model';
import { CbComplaintTypeService } from '../service/cb-complaint-type.service';
import { CbComplaintTypeFormService, CbComplaintTypeFormGroup } from './cb-complaint-type-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cb-complaint-type-update',
  templateUrl: './cb-complaint-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CbComplaintTypeUpdateComponent implements OnInit {
  isSaving = false;
  cbComplaintType: ICbComplaintType | null = null;

  protected cbComplaintTypeService = inject(CbComplaintTypeService);
  protected cbComplaintTypeFormService = inject(CbComplaintTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CbComplaintTypeFormGroup = this.cbComplaintTypeFormService.createCbComplaintTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbComplaintType }) => {
      this.cbComplaintType = cbComplaintType;
      if (cbComplaintType) {
        this.updateForm(cbComplaintType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbComplaintType = this.cbComplaintTypeFormService.getCbComplaintType(this.editForm);
    if (cbComplaintType.id !== null) {
      this.subscribeToSaveResponse(this.cbComplaintTypeService.update(cbComplaintType));
    } else {
      this.subscribeToSaveResponse(this.cbComplaintTypeService.create(cbComplaintType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbComplaintType>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(cbComplaintType: ICbComplaintType): void {
    this.cbComplaintType = cbComplaintType;
    this.cbComplaintTypeFormService.resetForm(this.editForm, cbComplaintType);
  }
}
