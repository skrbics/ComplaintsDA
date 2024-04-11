import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ComplaintAttachmentComponent } from './list/complaint-attachment.component';
import { ComplaintAttachmentDetailComponent } from './detail/complaint-attachment-detail.component';
import { ComplaintAttachmentUpdateComponent } from './update/complaint-attachment-update.component';
import ComplaintAttachmentResolve from './route/complaint-attachment-routing-resolve.service';

const complaintAttachmentRoute: Routes = [
  {
    path: '',
    component: ComplaintAttachmentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ComplaintAttachmentDetailComponent,
    resolve: {
      complaintAttachment: ComplaintAttachmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ComplaintAttachmentUpdateComponent,
    resolve: {
      complaintAttachment: ComplaintAttachmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ComplaintAttachmentUpdateComponent,
    resolve: {
      complaintAttachment: ComplaintAttachmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default complaintAttachmentRoute;
