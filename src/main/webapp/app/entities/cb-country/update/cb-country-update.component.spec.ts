import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CbCountryService } from '../service/cb-country.service';
import { ICbCountry } from '../cb-country.model';
import { CbCountryFormService } from './cb-country-form.service';

import { CbCountryUpdateComponent } from './cb-country-update.component';

describe('CbCountry Management Update Component', () => {
  let comp: CbCountryUpdateComponent;
  let fixture: ComponentFixture<CbCountryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbCountryFormService: CbCountryFormService;
  let cbCountryService: CbCountryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CbCountryUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CbCountryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbCountryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbCountryFormService = TestBed.inject(CbCountryFormService);
    cbCountryService = TestBed.inject(CbCountryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cbCountry: ICbCountry = { id: 456 };

      activatedRoute.data = of({ cbCountry });
      comp.ngOnInit();

      expect(comp.cbCountry).toEqual(cbCountry);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbCountry>>();
      const cbCountry = { id: 123 };
      jest.spyOn(cbCountryFormService, 'getCbCountry').mockReturnValue(cbCountry);
      jest.spyOn(cbCountryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbCountry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbCountry }));
      saveSubject.complete();

      // THEN
      expect(cbCountryFormService.getCbCountry).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbCountryService.update).toHaveBeenCalledWith(expect.objectContaining(cbCountry));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbCountry>>();
      const cbCountry = { id: 123 };
      jest.spyOn(cbCountryFormService, 'getCbCountry').mockReturnValue({ id: null });
      jest.spyOn(cbCountryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbCountry: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbCountry }));
      saveSubject.complete();

      // THEN
      expect(cbCountryFormService.getCbCountry).toHaveBeenCalled();
      expect(cbCountryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbCountry>>();
      const cbCountry = { id: 123 };
      jest.spyOn(cbCountryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbCountry });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbCountryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
