import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbComplaintSubField } from '../cb-complaint-sub-field.model';
import { CbComplaintSubFieldService } from '../service/cb-complaint-sub-field.service';

const cbComplaintSubFieldResolve = (route: ActivatedRouteSnapshot): Observable<null | ICbComplaintSubField> => {
  const id = route.params['id'];
  if (id) {
    return inject(CbComplaintSubFieldService)
      .find(id)
      .pipe(
        mergeMap((cbComplaintSubField: HttpResponse<ICbComplaintSubField>) => {
          if (cbComplaintSubField.body) {
            return of(cbComplaintSubField.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cbComplaintSubFieldResolve;
