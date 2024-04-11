import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbComplaintType } from '../cb-complaint-type.model';
import { CbComplaintTypeService } from '../service/cb-complaint-type.service';

const cbComplaintTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | ICbComplaintType> => {
  const id = route.params['id'];
  if (id) {
    return inject(CbComplaintTypeService)
      .find(id)
      .pipe(
        mergeMap((cbComplaintType: HttpResponse<ICbComplaintType>) => {
          if (cbComplaintType.body) {
            return of(cbComplaintType.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cbComplaintTypeResolve;
