import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOperator } from '../operator.model';
import { OperatorService } from '../service/operator.service';
import { OperatorFormService, OperatorFormGroup } from './operator-form.service';

@Component({
  standalone: true,
  selector: 'jhi-operator-update',
  templateUrl: './operator-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OperatorUpdateComponent implements OnInit {
  isSaving = false;
  operator: IOperator | null = null;

  protected operatorService = inject(OperatorService);
  protected operatorFormService = inject(OperatorFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OperatorFormGroup = this.operatorFormService.createOperatorFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operator }) => {
      this.operator = operator;
      if (operator) {
        this.updateForm(operator);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operator = this.operatorFormService.getOperator(this.editForm);
    if (operator.id !== null) {
      this.subscribeToSaveResponse(this.operatorService.update(operator));
    } else {
      this.subscribeToSaveResponse(this.operatorService.create(operator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperator>>): void {
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

  protected updateForm(operator: IOperator): void {
    this.operator = operator;
    this.operatorFormService.resetForm(this.editForm, operator);
  }
}
