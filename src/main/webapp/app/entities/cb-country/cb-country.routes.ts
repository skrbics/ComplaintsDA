import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CbCountryComponent } from './list/cb-country.component';
import { CbCountryDetailComponent } from './detail/cb-country-detail.component';
import { CbCountryUpdateComponent } from './update/cb-country-update.component';
import CbCountryResolve from './route/cb-country-routing-resolve.service';

const cbCountryRoute: Routes = [
  {
    path: '',
    component: CbCountryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbCountryDetailComponent,
    resolve: {
      cbCountry: CbCountryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbCountryUpdateComponent,
    resolve: {
      cbCountry: CbCountryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbCountryUpdateComponent,
    resolve: {
      cbCountry: CbCountryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cbCountryRoute;
