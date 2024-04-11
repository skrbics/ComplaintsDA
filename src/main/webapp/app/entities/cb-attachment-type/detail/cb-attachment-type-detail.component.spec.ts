import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CbAttachmentTypeDetailComponent } from './cb-attachment-type-detail.component';

describe('CbAttachmentType Management Detail Component', () => {
  let comp: CbAttachmentTypeDetailComponent;
  let fixture: ComponentFixture<CbAttachmentTypeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CbAttachmentTypeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CbAttachmentTypeDetailComponent,
              resolve: { cbAttachmentType: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CbAttachmentTypeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CbAttachmentTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cbAttachmentType on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CbAttachmentTypeDetailComponent);

      // THEN
      expect(instance.cbAttachmentType()).toEqual(expect.objectContaining({ id: 123 }));
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
