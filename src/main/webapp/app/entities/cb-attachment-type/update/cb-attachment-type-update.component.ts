import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbAttachmentType } from '../cb-attachment-type.model';
import { CbAttachmentTypeService } from '../service/cb-attachment-type.service';
import { CbAttachmentTypeFormService, CbAttachmentTypeFormGroup } from './cb-attachment-type-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cb-attachment-type-update',
  templateUrl: './cb-attachment-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CbAttachmentTypeUpdateComponent implements OnInit {
  isSaving = false;
  cbAttachmentType: ICbAttachmentType | null = null;

  protected cbAttachmentTypeService = inject(CbAttachmentTypeService);
  protected cbAttachmentTypeFormService = inject(CbAttachmentTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CbAttachmentTypeFormGroup = this.cbAttachmentTypeFormService.createCbAttachmentTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbAttachmentType }) => {
      this.cbAttachmentType = cbAttachmentType;
      if (cbAttachmentType) {
        this.updateForm(cbAttachmentType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbAttachmentType = this.cbAttachmentTypeFormService.getCbAttachmentType(this.editForm);
    if (cbAttachmentType.id !== null) {
      this.subscribeToSaveResponse(this.cbAttachmentTypeService.update(cbAttachmentType));
    } else {
      this.subscribeToSaveResponse(this.cbAttachmentTypeService.create(cbAttachmentType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbAttachmentType>>): void {
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

  protected updateForm(cbAttachmentType: ICbAttachmentType): void {
    this.cbAttachmentType = cbAttachmentType;
    this.cbAttachmentTypeFormService.resetForm(this.editForm, cbAttachmentType);
  }
}
