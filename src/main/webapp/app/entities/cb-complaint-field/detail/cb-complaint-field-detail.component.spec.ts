import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CbComplaintFieldDetailComponent } from './cb-complaint-field-detail.component';

describe('CbComplaintField Management Detail Component', () => {
  let comp: CbComplaintFieldDetailComponent;
  let fixture: ComponentFixture<CbComplaintFieldDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CbComplaintFieldDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CbComplaintFieldDetailComponent,
              resolve: { cbComplaintField: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CbComplaintFieldDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CbComplaintFieldDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cbComplaintField on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CbComplaintFieldDetailComponent);

      // THEN
      expect(instance.cbComplaintField()).toEqual(expect.objectContaining({ id: 123 }));
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
