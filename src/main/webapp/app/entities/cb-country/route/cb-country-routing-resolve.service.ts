import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbCountry } from '../cb-country.model';
import { CbCountryService } from '../service/cb-country.service';

const cbCountryResolve = (route: ActivatedRouteSnapshot): Observable<null | ICbCountry> => {
  const id = route.params['id'];
  if (id) {
    return inject(CbCountryService)
      .find(id)
      .pipe(
        mergeMap((cbCountry: HttpResponse<ICbCountry>) => {
          if (cbCountry.body) {
            return of(cbCountry.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cbCountryResolve;
