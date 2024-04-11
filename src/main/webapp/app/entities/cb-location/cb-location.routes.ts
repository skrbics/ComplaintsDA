import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CbLocationComponent } from './list/cb-location.component';
import { CbLocationDetailComponent } from './detail/cb-location-detail.component';
import { CbLocationUpdateComponent } from './update/cb-location-update.component';
import CbLocationResolve from './route/cb-location-routing-resolve.service';

const cbLocationRoute: Routes = [
  {
    path: '',
    component: CbLocationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbLocationDetailComponent,
    resolve: {
      cbLocation: CbLocationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbLocationUpdateComponent,
    resolve: {
      cbLocation: CbLocationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbLocationUpdateComponent,
    resolve: {
      cbLocation: CbLocationResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cbLocationRoute;
