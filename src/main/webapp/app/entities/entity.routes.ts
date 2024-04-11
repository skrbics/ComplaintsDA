import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'complaintsDaApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'complaint',
    data: { pageTitle: 'complaintsDaApp.complaint.home.title' },
    loadChildren: () => import('./complaint/complaint.routes'),
  },
  {
    path: 'complaint-attachment',
    data: { pageTitle: 'complaintsDaApp.complaintAttachment.home.title' },
    loadChildren: () => import('./complaint-attachment/complaint-attachment.routes'),
  },
  {
    path: 'applicant',
    data: { pageTitle: 'complaintsDaApp.applicant.home.title' },
    loadChildren: () => import('./applicant/applicant.routes'),
  },
  {
    path: 'operator',
    data: { pageTitle: 'complaintsDaApp.operator.home.title' },
    loadChildren: () => import('./operator/operator.routes'),
  },
  {
    path: 'address',
    data: { pageTitle: 'complaintsDaApp.address.home.title' },
    loadChildren: () => import('./address/address.routes'),
  },
  {
    path: 'cb-complaint-phase',
    data: { pageTitle: 'complaintsDaApp.cbComplaintPhase.home.title' },
    loadChildren: () => import('./cb-complaint-phase/cb-complaint-phase.routes'),
  },
  {
    path: 'cb-complaint-field',
    data: { pageTitle: 'complaintsDaApp.cbComplaintField.home.title' },
    loadChildren: () => import('./cb-complaint-field/cb-complaint-field.routes'),
  },
  {
    path: 'cb-complaint-sub-field',
    data: { pageTitle: 'complaintsDaApp.cbComplaintSubField.home.title' },
    loadChildren: () => import('./cb-complaint-sub-field/cb-complaint-sub-field.routes'),
  },
  {
    path: 'cb-complaint-type',
    data: { pageTitle: 'complaintsDaApp.cbComplaintType.home.title' },
    loadChildren: () => import('./cb-complaint-type/cb-complaint-type.routes'),
  },
  {
    path: 'cb-complaint-channel',
    data: { pageTitle: 'complaintsDaApp.cbComplaintChannel.home.title' },
    loadChildren: () => import('./cb-complaint-channel/cb-complaint-channel.routes'),
  },
  {
    path: 'cb-applicant-capacity-type',
    data: { pageTitle: 'complaintsDaApp.cbApplicantCapacityType.home.title' },
    loadChildren: () => import('./cb-applicant-capacity-type/cb-applicant-capacity-type.routes'),
  },
  {
    path: 'cb-location',
    data: { pageTitle: 'complaintsDaApp.cbLocation.home.title' },
    loadChildren: () => import('./cb-location/cb-location.routes'),
  },
  {
    path: 'cb-attachment-type',
    data: { pageTitle: 'complaintsDaApp.cbAttachmentType.home.title' },
    loadChildren: () => import('./cb-attachment-type/cb-attachment-type.routes'),
  },
  {
    path: 'cb-country',
    data: { pageTitle: 'complaintsDaApp.cbCountry.home.title' },
    loadChildren: () => import('./cb-country/cb-country.routes'),
  },
  {
    path: 'cb-city',
    data: { pageTitle: 'complaintsDaApp.cbCity.home.title' },
    loadChildren: () => import('./cb-city/cb-city.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
