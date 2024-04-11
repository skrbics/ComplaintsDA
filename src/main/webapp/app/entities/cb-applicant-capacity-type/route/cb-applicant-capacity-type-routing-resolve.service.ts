import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbApplicantCapacityType } from '../cb-applicant-capacity-type.model';
import { CbApplicantCapacityTypeService } from '../service/cb-applicant-capacity-type.service';

const cbApplicantCapacityTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | ICbApplicantCapacityType> => {
  const id = route.params['id'];
  if (id) {
    return inject(CbApplicantCapacityTypeService)
      .find(id)
      .pipe(
        mergeMap((cbApplicantCapacityType: HttpResponse<ICbApplicantCapacityType>) => {
          if (cbApplicantCapacityType.body) {
            return of(cbApplicantCapacityType.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cbApplicantCapacityTypeResolve;
