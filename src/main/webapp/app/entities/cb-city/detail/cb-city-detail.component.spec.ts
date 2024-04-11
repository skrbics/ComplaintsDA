import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CbCityDetailComponent } from './cb-city-detail.component';

describe('CbCity Management Detail Component', () => {
  let comp: CbCityDetailComponent;
  let fixture: ComponentFixture<CbCityDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CbCityDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CbCityDetailComponent,
              resolve: { cbCity: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CbCityDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CbCityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cbCity on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CbCityDetailComponent);

      // THEN
      expect(instance.cbCity()).toEqual(expect.objectContaining({ id: 123 }));
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
