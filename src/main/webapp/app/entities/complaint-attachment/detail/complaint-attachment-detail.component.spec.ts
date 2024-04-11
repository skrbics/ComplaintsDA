import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ComplaintAttachmentDetailComponent } from './complaint-attachment-detail.component';

describe('ComplaintAttachment Management Detail Component', () => {
  let comp: ComplaintAttachmentDetailComponent;
  let fixture: ComponentFixture<ComplaintAttachmentDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComplaintAttachmentDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ComplaintAttachmentDetailComponent,
              resolve: { complaintAttachment: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ComplaintAttachmentDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComplaintAttachmentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load complaintAttachment on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ComplaintAttachmentDetailComponent);

      // THEN
      expect(instance.complaintAttachment()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
