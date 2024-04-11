import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbLocation } from '../cb-location.model';
import { CbLocationService } from '../service/cb-location.service';

const cbLocationResolve = (route: ActivatedRouteSnapshot): Observable<null | ICbLocation> => {
  const id = route.params['id'];
  if (id) {
    return inject(CbLocationService)
      .find(id)
      .pipe(
        mergeMap((cbLocation: HttpResponse<ICbLocation>) => {
          if (cbLocation.body) {
            return of(cbLocation.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cbLocationResolve;
