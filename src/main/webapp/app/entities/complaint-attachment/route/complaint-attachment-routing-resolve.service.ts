import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IComplaintAttachment } from '../complaint-attachment.model';
import { ComplaintAttachmentService } from '../service/complaint-attachment.service';

const complaintAttachmentResolve = (route: ActivatedRouteSnapshot): Observable<null | IComplaintAttachment> => {
  const id = route.params['id'];
  if (id) {
    return inject(ComplaintAttachmentService)
      .find(id)
      .pipe(
        mergeMap((complaintAttachment: HttpResponse<IComplaintAttachment>) => {
          if (complaintAttachment.body) {
            return of(complaintAttachment.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default complaintAttachmentResolve;
