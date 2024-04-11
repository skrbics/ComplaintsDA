import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbComplaintField } from '../cb-complaint-field.model';
import { CbComplaintFieldService } from '../service/cb-complaint-field.service';

const cbComplaintFieldResolve = (route: ActivatedRouteSnapshot): Observable<null | ICbComplaintField> => {
  const id = route.params['id'];
  if (id) {
    return inject(CbComplaintFieldService)
      .find(id)
      .pipe(
        mergeMap((cbComplaintField: HttpResponse<ICbComplaintField>) => {
          if (cbComplaintField.body) {
            return of(cbComplaintField.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cbComplaintFieldResolve;
