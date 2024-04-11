import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CbComplaintTypeComponent } from './list/cb-complaint-type.component';
import { CbComplaintTypeDetailComponent } from './detail/cb-complaint-type-detail.component';
import { CbComplaintTypeUpdateComponent } from './update/cb-complaint-type-update.component';
import CbComplaintTypeResolve from './route/cb-complaint-type-routing-resolve.service';

const cbComplaintTypeRoute: Routes = [
  {
    path: '',
    component: CbComplaintTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbComplaintTypeDetailComponent,
    resolve: {
      cbComplaintType: CbComplaintTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbComplaintTypeUpdateComponent,
    resolve: {
      cbComplaintType: CbComplaintTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbComplaintTypeUpdateComponent,
    resolve: {
      cbComplaintType: CbComplaintTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cbComplaintTypeRoute;
