import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbCity } from '../cb-city.model';
import { CbCityService } from '../service/cb-city.service';

const cbCityResolve = (route: ActivatedRouteSnapshot): Observable<null | ICbCity> => {
  const id = route.params['id'];
  if (id) {
    return inject(CbCityService)
      .find(id)
      .pipe(
        mergeMap((cbCity: HttpResponse<ICbCity>) => {
          if (cbCity.body) {
            return of(cbCity.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cbCityResolve;
