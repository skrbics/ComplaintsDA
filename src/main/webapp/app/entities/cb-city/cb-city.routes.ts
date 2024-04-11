import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CbCityComponent } from './list/cb-city.component';
import { CbCityDetailComponent } from './detail/cb-city-detail.component';
import { CbCityUpdateComponent } from './update/cb-city-update.component';
import CbCityResolve from './route/cb-city-routing-resolve.service';

const cbCityRoute: Routes = [
  {
    path: '',
    component: CbCityComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbCityDetailComponent,
    resolve: {
      cbCity: CbCityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbCityUpdateComponent,
    resolve: {
      cbCity: CbCityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbCityUpdateComponent,
    resolve: {
      cbCity: CbCityResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cbCityRoute;
