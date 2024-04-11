import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbComplaintPhase } from '../cb-complaint-phase.model';
import { CbComplaintPhaseService } from '../service/cb-complaint-phase.service';

const cbComplaintPhaseResolve = (route: ActivatedRouteSnapshot): Observable<null | ICbComplaintPhase> => {
  const id = route.params['id'];
  if (id) {
    return inject(CbComplaintPhaseService)
      .find(id)
      .pipe(
        mergeMap((cbComplaintPhase: HttpResponse<ICbComplaintPhase>) => {
          if (cbComplaintPhase.body) {
            return of(cbComplaintPhase.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cbComplaintPhaseResolve;
