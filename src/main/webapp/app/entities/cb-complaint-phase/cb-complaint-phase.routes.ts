import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CbComplaintPhaseComponent } from './list/cb-complaint-phase.component';
import { CbComplaintPhaseDetailComponent } from './detail/cb-complaint-phase-detail.component';
import { CbComplaintPhaseUpdateComponent } from './update/cb-complaint-phase-update.component';
import CbComplaintPhaseResolve from './route/cb-complaint-phase-routing-resolve.service';

const cbComplaintPhaseRoute: Routes = [
  {
    path: '',
    component: CbComplaintPhaseComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbComplaintPhaseDetailComponent,
    resolve: {
      cbComplaintPhase: CbComplaintPhaseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbComplaintPhaseUpdateComponent,
    resolve: {
      cbComplaintPhase: CbComplaintPhaseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbComplaintPhaseUpdateComponent,
    resolve: {
      cbComplaintPhase: CbComplaintPhaseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cbComplaintPhaseRoute;
