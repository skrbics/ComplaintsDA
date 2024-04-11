import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CbComplaintTypeDetailComponent } from './cb-complaint-type-detail.component';

describe('CbComplaintType Management Detail Component', () => {
  let comp: CbComplaintTypeDetailComponent;
  let fixture: ComponentFixture<CbComplaintTypeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CbComplaintTypeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CbComplaintTypeDetailComponent,
              resolve: { cbComplaintType: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CbComplaintTypeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CbComplaintTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cbComplaintType on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CbComplaintTypeDetailComponent);

      // THEN
      expect(instance.cbComplaintType()).toEqual(expect.objectContaining({ id: 123 }));
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
