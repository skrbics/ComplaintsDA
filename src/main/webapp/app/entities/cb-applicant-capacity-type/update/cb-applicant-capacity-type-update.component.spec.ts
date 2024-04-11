import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CbApplicantCapacityTypeService } from '../service/cb-applicant-capacity-type.service';
import { ICbApplicantCapacityType } from '../cb-applicant-capacity-type.model';
import { CbApplicantCapacityTypeFormService } from './cb-applicant-capacity-type-form.service';

import { CbApplicantCapacityTypeUpdateComponent } from './cb-applicant-capacity-type-update.component';

describe('CbApplicantCapacityType Management Update Component', () => {
  let comp: CbApplicantCapacityTypeUpdateComponent;
  let fixture: ComponentFixture<CbApplicantCapacityTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbApplicantCapacityTypeFormService: CbApplicantCapacityTypeFormService;
  let cbApplicantCapacityTypeService: CbApplicantCapacityTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CbApplicantCapacityTypeUpdateComponent],
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
      .overrideTemplate(CbApplicantCapacityTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbApplicantCapacityTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbApplicantCapacityTypeFormService = TestBed.inject(CbApplicantCapacityTypeFormService);
    cbApplicantCapacityTypeService = TestBed.inject(CbApplicantCapacityTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cbApplicantCapacityType: ICbApplicantCapacityType = { id: 456 };

      activatedRoute.data = of({ cbApplicantCapacityType });
      comp.ngOnInit();

      expect(comp.cbApplicantCapacityType).toEqual(cbApplicantCapacityType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbApplicantCapacityType>>();
      const cbApplicantCapacityType = { id: 123 };
      jest.spyOn(cbApplicantCapacityTypeFormService, 'getCbApplicantCapacityType').mockReturnValue(cbApplicantCapacityType);
      jest.spyOn(cbApplicantCapacityTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbApplicantCapacityType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbApplicantCapacityType }));
      saveSubject.complete();

      // THEN
      expect(cbApplicantCapacityTypeFormService.getCbApplicantCapacityType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbApplicantCapacityTypeService.update).toHaveBeenCalledWith(expect.objectContaining(cbApplicantCapacityType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbApplicantCapacityType>>();
      const cbApplicantCapacityType = { id: 123 };
      jest.spyOn(cbApplicantCapacityTypeFormService, 'getCbApplicantCapacityType').mockReturnValue({ id: null });
      jest.spyOn(cbApplicantCapacityTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbApplicantCapacityType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbApplicantCapacityType }));
      saveSubject.complete();

      // THEN
      expect(cbApplicantCapacityTypeFormService.getCbApplicantCapacityType).toHaveBeenCalled();
      expect(cbApplicantCapacityTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbApplicantCapacityType>>();
      const cbApplicantCapacityType = { id: 123 };
      jest.spyOn(cbApplicantCapacityTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbApplicantCapacityType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbApplicantCapacityTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
