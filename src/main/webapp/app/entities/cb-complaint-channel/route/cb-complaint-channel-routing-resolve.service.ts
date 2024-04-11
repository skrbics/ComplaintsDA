import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICbComplaintChannel } from '../cb-complaint-channel.model';
import { CbComplaintChannelService } from '../service/cb-complaint-channel.service';

const cbComplaintChannelResolve = (route: ActivatedRouteSnapshot): Observable<null | ICbComplaintChannel> => {
  const id = route.params['id'];
  if (id) {
    return inject(CbComplaintChannelService)
      .find(id)
      .pipe(
        mergeMap((cbComplaintChannel: HttpResponse<ICbComplaintChannel>) => {
          if (cbComplaintChannel.body) {
            return of(cbComplaintChannel.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cbComplaintChannelResolve;
