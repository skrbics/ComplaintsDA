import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CbComplaintFieldComponent } from './list/cb-complaint-field.component';
import { CbComplaintFieldDetailComponent } from './detail/cb-complaint-field-detail.component';
import { CbComplaintFieldUpdateComponent } from './update/cb-complaint-field-update.component';
import CbComplaintFieldResolve from './route/cb-complaint-field-routing-resolve.service';

const cbComplaintFieldRoute: Routes = [
  {
    path: '',
    component: CbComplaintFieldComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbComplaintFieldDetailComponent,
    resolve: {
      cbComplaintField: CbComplaintFieldResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbComplaintFieldUpdateComponent,
    resolve: {
      cbComplaintField: CbComplaintFieldResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbComplaintFieldUpdateComponent,
    resolve: {
      cbComplaintField: CbComplaintFieldResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cbComplaintFieldRoute;
