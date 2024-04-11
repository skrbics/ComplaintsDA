import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CbCityService } from '../service/cb-city.service';
import { ICbCity } from '../cb-city.model';
import { CbCityFormService } from './cb-city-form.service';

import { CbCityUpdateComponent } from './cb-city-update.component';

describe('CbCity Management Update Component', () => {
  let comp: CbCityUpdateComponent;
  let fixture: ComponentFixture<CbCityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbCityFormService: CbCityFormService;
  let cbCityService: CbCityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CbCityUpdateComponent],
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
      .overrideTemplate(CbCityUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbCityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbCityFormService = TestBed.inject(CbCityFormService);
    cbCityService = TestBed.inject(CbCityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cbCity: ICbCity = { id: 456 };

      activatedRoute.data = of({ cbCity });
      comp.ngOnInit();

      expect(comp.cbCity).toEqual(cbCity);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbCity>>();
      const cbCity = { id: 123 };
      jest.spyOn(cbCityFormService, 'getCbCity').mockReturnValue(cbCity);
      jest.spyOn(cbCityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbCity });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbCity }));
      saveSubject.complete();

      // THEN
      expect(cbCityFormService.getCbCity).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbCityService.update).toHaveBeenCalledWith(expect.objectContaining(cbCity));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbCity>>();
      const cbCity = { id: 123 };
      jest.spyOn(cbCityFormService, 'getCbCity').mockReturnValue({ id: null });
      jest.spyOn(cbCityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbCity: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbCity }));
      saveSubject.complete();

      // THEN
      expect(cbCityFormService.getCbCity).toHaveBeenCalled();
      expect(cbCityService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbCity>>();
      const cbCity = { id: 123 };
      jest.spyOn(cbCityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbCity });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbCityService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
