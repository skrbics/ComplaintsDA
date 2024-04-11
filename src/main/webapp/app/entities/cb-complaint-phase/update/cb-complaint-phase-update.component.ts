import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbComplaintPhase } from '../cb-complaint-phase.model';
import { CbComplaintPhaseService } from '../service/cb-complaint-phase.service';
import { CbComplaintPhaseFormService, CbComplaintPhaseFormGroup } from './cb-complaint-phase-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cb-complaint-phase-update',
  templateUrl: './cb-complaint-phase-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CbComplaintPhaseUpdateComponent implements OnInit {
  isSaving = false;
  cbComplaintPhase: ICbComplaintPhase | null = null;

  protected cbComplaintPhaseService = inject(CbComplaintPhaseService);
  protected cbComplaintPhaseFormService = inject(CbComplaintPhaseFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CbComplaintPhaseFormGroup = this.cbComplaintPhaseFormService.createCbComplaintPhaseFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbComplaintPhase }) => {
      this.cbComplaintPhase = cbComplaintPhase;
      if (cbComplaintPhase) {
        this.updateForm(cbComplaintPhase);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbComplaintPhase = this.cbComplaintPhaseFormService.getCbComplaintPhase(this.editForm);
    if (cbComplaintPhase.id !== null) {
      this.subscribeToSaveResponse(this.cbComplaintPhaseService.update(cbComplaintPhase));
    } else {
      this.subscribeToSaveResponse(this.cbComplaintPhaseService.create(cbComplaintPhase));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbComplaintPhase>>): void {
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

  protected updateForm(cbComplaintPhase: ICbComplaintPhase): void {
    this.cbComplaintPhase = cbComplaintPhase;
    this.cbComplaintPhaseFormService.resetForm(this.editForm, cbComplaintPhase);
  }
}
