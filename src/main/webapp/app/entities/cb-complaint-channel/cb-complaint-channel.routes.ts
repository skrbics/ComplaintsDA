import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CbComplaintChannelComponent } from './list/cb-complaint-channel.component';
import { CbComplaintChannelDetailComponent } from './detail/cb-complaint-channel-detail.component';
import { CbComplaintChannelUpdateComponent } from './update/cb-complaint-channel-update.component';
import CbComplaintChannelResolve from './route/cb-complaint-channel-routing-resolve.service';

const cbComplaintChannelRoute: Routes = [
  {
    path: '',
    component: CbComplaintChannelComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbComplaintChannelDetailComponent,
    resolve: {
      cbComplaintChannel: CbComplaintChannelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbComplaintChannelUpdateComponent,
    resolve: {
      cbComplaintChannel: CbComplaintChannelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbComplaintChannelUpdateComponent,
    resolve: {
      cbComplaintChannel: CbComplaintChannelResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cbComplaintChannelRoute;
