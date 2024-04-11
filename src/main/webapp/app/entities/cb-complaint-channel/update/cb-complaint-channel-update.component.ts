import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICbComplaintChannel } from '../cb-complaint-channel.model';
import { CbComplaintChannelService } from '../service/cb-complaint-channel.service';
import { CbComplaintChannelFormService, CbComplaintChannelFormGroup } from './cb-complaint-channel-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cb-complaint-channel-update',
  templateUrl: './cb-complaint-channel-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CbComplaintChannelUpdateComponent implements OnInit {
  isSaving = false;
  cbComplaintChannel: ICbComplaintChannel | null = null;

  protected cbComplaintChannelService = inject(CbComplaintChannelService);
  protected cbComplaintChannelFormService = inject(CbComplaintChannelFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CbComplaintChannelFormGroup = this.cbComplaintChannelFormService.createCbComplaintChannelFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cbComplaintChannel }) => {
      this.cbComplaintChannel = cbComplaintChannel;
      if (cbComplaintChannel) {
        this.updateForm(cbComplaintChannel);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cbComplaintChannel = this.cbComplaintChannelFormService.getCbComplaintChannel(this.editForm);
    if (cbComplaintChannel.id !== null) {
      this.subscribeToSaveResponse(this.cbComplaintChannelService.update(cbComplaintChannel));
    } else {
      this.subscribeToSaveResponse(this.cbComplaintChannelService.create(cbComplaintChannel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICbComplaintChannel>>): void {
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

  protected updateForm(cbComplaintChannel: ICbComplaintChannel): void {
    this.cbComplaintChannel = cbComplaintChannel;
    this.cbComplaintChannelFormService.resetForm(this.editForm, cbComplaintChannel);
  }
}
