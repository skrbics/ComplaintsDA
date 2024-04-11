import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CbCountryDetailComponent } from './cb-country-detail.component';

describe('CbCountry Management Detail Component', () => {
  let comp: CbCountryDetailComponent;
  let fixture: ComponentFixture<CbCountryDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CbCountryDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CbCountryDetailComponent,
              resolve: { cbCountry: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CbCountryDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CbCountryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cbCountry on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CbCountryDetailComponent);

      // THEN
      expect(instance.cbCountry()).toEqual(expect.objectContaining({ id: 123 }));
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
