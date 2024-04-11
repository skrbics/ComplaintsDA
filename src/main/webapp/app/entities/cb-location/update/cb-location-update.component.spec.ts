import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICbCity } from 'app/entities/cb-city/cb-city.model';
import { CbCityService } from 'app/entities/cb-city/service/cb-city.service';
import { CbLocationService } from '../service/cb-location.service';
import { ICbLocation } from '../cb-location.model';
import { CbLocationFormService } from './cb-location-form.service';

import { CbLocationUpdateComponent } from './cb-location-update.component';

describe('CbLocation Management Update Component', () => {
  let comp: CbLocationUpdateComponent;
  let fixture: ComponentFixture<CbLocationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbLocationFormService: CbLocationFormService;
  let cbLocationService: CbLocationService;
  let cbCityService: CbCityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CbLocationUpdateComponent],
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
      .overrideTemplate(CbLocationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbLocationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbLocationFormService = TestBed.inject(CbLocationFormService);
    cbLocationService = TestBed.inject(CbLocationService);
    cbCityService = TestBed.inject(CbCityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CbCity query and add missing value', () => {
      const cbLocation: ICbLocation = { id: 456 };
      const cbCity: ICbCity = { id: 31298 };
      cbLocation.cbCity = cbCity;

      const cbCityCollection: ICbCity[] = [{ id: 30154 }];
      jest.spyOn(cbCityService, 'query').mockReturnValue(of(new HttpResponse({ body: cbCityCollection })));
      const additionalCbCities = [cbCity];
      const expectedCollection: ICbCity[] = [...additionalCbCities, ...cbCityCollection];
      jest.spyOn(cbCityService, 'addCbCityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cbLocation });
      comp.ngOnInit();

      expect(cbCityService.query).toHaveBeenCalled();
      expect(cbCityService.addCbCityToCollectionIfMissing).toHaveBeenCalledWith(
        cbCityCollection,
        ...additionalCbCities.map(expect.objectContaining),
      );
      expect(comp.cbCitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cbLocation: ICbLocation = { id: 456 };
      const cbCity: ICbCity = { id: 457 };
      cbLocation.cbCity = cbCity;

      activatedRoute.data = of({ cbLocation });
      comp.ngOnInit();

      expect(comp.cbCitiesSharedCollection).toContain(cbCity);
      expect(comp.cbLocation).toEqual(cbLocation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbLocation>>();
      const cbLocation = { id: 123 };
      jest.spyOn(cbLocationFormService, 'getCbLocation').mockReturnValue(cbLocation);
      jest.spyOn(cbLocationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbLocation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbLocation }));
      saveSubject.complete();

      // THEN
      expect(cbLocationFormService.getCbLocation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbLocationService.update).toHaveBeenCalledWith(expect.objectContaining(cbLocation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbLocation>>();
      const cbLocation = { id: 123 };
      jest.spyOn(cbLocationFormService, 'getCbLocation').mockReturnValue({ id: null });
      jest.spyOn(cbLocationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbLocation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbLocation }));
      saveSubject.complete();

      // THEN
      expect(cbLocationFormService.getCbLocation).toHaveBeenCalled();
      expect(cbLocationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbLocation>>();
      const cbLocation = { id: 123 };
      jest.spyOn(cbLocationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbLocation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbLocationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCbCity', () => {
      it('Should forward to cbCityService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cbCityService, 'compareCbCity');
        comp.compareCbCity(entity, entity2);
        expect(cbCityService.compareCbCity).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
