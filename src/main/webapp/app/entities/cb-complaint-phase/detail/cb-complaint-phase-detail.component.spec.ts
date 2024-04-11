import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CbComplaintPhaseDetailComponent } from './cb-complaint-phase-detail.component';

describe('CbComplaintPhase Management Detail Component', () => {
  let comp: CbComplaintPhaseDetailComponent;
  let fixture: ComponentFixture<CbComplaintPhaseDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CbComplaintPhaseDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CbComplaintPhaseDetailComponent,
              resolve: { cbComplaintPhase: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CbComplaintPhaseDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CbComplaintPhaseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cbComplaintPhase on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CbComplaintPhaseDetailComponent);

      // THEN
      expect(instance.cbComplaintPhase()).toEqual(expect.objectContaining({ id: 123 }));
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
