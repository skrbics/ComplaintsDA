import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CbApplicantCapacityTypeDetailComponent } from './cb-applicant-capacity-type-detail.component';

describe('CbApplicantCapacityType Management Detail Component', () => {
  let comp: CbApplicantCapacityTypeDetailComponent;
  let fixture: ComponentFixture<CbApplicantCapacityTypeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CbApplicantCapacityTypeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CbApplicantCapacityTypeDetailComponent,
              resolve: { cbApplicantCapacityType: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CbApplicantCapacityTypeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CbApplicantCapacityTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cbApplicantCapacityType on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CbApplicantCapacityTypeDetailComponent);

      // THEN
      expect(instance.cbApplicantCapacityType()).toEqual(expect.objectContaining({ id: 123 }));
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
