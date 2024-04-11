import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CbComplaintSubFieldComponent } from './list/cb-complaint-sub-field.component';
import { CbComplaintSubFieldDetailComponent } from './detail/cb-complaint-sub-field-detail.component';
import { CbComplaintSubFieldUpdateComponent } from './update/cb-complaint-sub-field-update.component';
import CbComplaintSubFieldResolve from './route/cb-complaint-sub-field-routing-resolve.service';

const cbComplaintSubFieldRoute: Routes = [
  {
    path: '',
    component: CbComplaintSubFieldComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbComplaintSubFieldDetailComponent,
    resolve: {
      cbComplaintSubField: CbComplaintSubFieldResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbComplaintSubFieldUpdateComponent,
    resolve: {
      cbComplaintSubField: CbComplaintSubFieldResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbComplaintSubFieldUpdateComponent,
    resolve: {
      cbComplaintSubField: CbComplaintSubFieldResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cbComplaintSubFieldRoute;
