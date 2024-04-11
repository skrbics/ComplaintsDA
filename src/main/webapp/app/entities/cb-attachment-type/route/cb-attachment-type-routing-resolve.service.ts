import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbAttachmentType } from '../cb-attachment-type.model';
import { CbAttachmentTypeService } from '../service/cb-attachment-type.service';

const cbAttachmentTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | ICbAttachmentType> => {
  const id = route.params['id'];
  if (id) {
    return inject(CbAttachmentTypeService)
      .find(id)
      .pipe(
        mergeMap((cbAttachmentType: HttpResponse<ICbAttachmentType>) => {
          if (cbAttachmentType.body) {
            return of(cbAttachmentType.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cbAttachmentTypeResolve;
