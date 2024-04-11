import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CbComplaintSubFieldDetailComponent } from './cb-complaint-sub-field-detail.component';

describe('CbComplaintSubField Management Detail Component', () => {
  let comp: CbComplaintSubFieldDetailComponent;
  let fixture: ComponentFixture<CbComplaintSubFieldDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CbComplaintSubFieldDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CbComplaintSubFieldDetailComponent,
              resolve: { cbComplaintSubField: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CbComplaintSubFieldDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CbComplaintSubFieldDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cbComplaintSubField on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CbComplaintSubFieldDetailComponent);

      // THEN
      expect(instance.cbComplaintSubField()).toEqual(expect.objectContaining({ id: 123 }));
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
