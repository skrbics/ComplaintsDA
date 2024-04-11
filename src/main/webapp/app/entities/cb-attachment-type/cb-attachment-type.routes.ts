import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CbAttachmentTypeComponent } from './list/cb-attachment-type.component';
import { CbAttachmentTypeDetailComponent } from './detail/cb-attachment-type-detail.component';
import { CbAttachmentTypeUpdateComponent } from './update/cb-attachment-type-update.component';
import CbAttachmentTypeResolve from './route/cb-attachment-type-routing-resolve.service';

const cbAttachmentTypeRoute: Routes = [
  {
    path: '',
    component: CbAttachmentTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CbAttachmentTypeDetailComponent,
    resolve: {
      cbAttachmentType: CbAttachmentTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CbAttachmentTypeUpdateComponent,
    resolve: {
      cbAttachmentType: CbAttachmentTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CbAttachmentTypeUpdateComponent,
    resolve: {
      cbAttachmentType: CbAttachmentTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cbAttachmentTypeRoute;
