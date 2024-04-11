import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CbApplicantCapacityTypeComponent } from './list/cb-applicant-capacity-type.component';
import { CbApplicantCapacityTypeDetailComponent } from './detail/cb-applicant-capacity-type-detail.component';
import { CbApplicantCapacityTypeUpdateComponent } from './update/cb-applicant-capacity-type-update.component';
import CbApplicantCapacityTypeResolve from './route/cb-applicant-capacity-type-routing-resolve.service';

const cbApplicantCapacityTypeRoute: Routes = [
  {
    path: '',
    component: CbApplicantCapacityTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbApplicantCapacityTypeDetailComponent,
    resolve: {
      cbApplicantCapacityType: CbApplicantCapacityTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbApplicantCapacityTypeUpdateComponent,
    resolve: {
      cbApplicantCapacityType: CbApplicantCapacityTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbApplicantCapacityTypeUpdateComponent,
    resolve: {
      cbApplicantCapacityType: CbApplicantCapacityTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cbApplicantCapacityTypeRoute;
