import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CbComplaintTypeService } from '../service/cb-complaint-type.service';
import { ICbComplaintType } from '../cb-complaint-type.model';
import { CbComplaintTypeFormService } from './cb-complaint-type-form.service';

import { CbComplaintTypeUpdateComponent } from './cb-complaint-type-update.component';

describe('CbComplaintType Management Update Component', () => {
  let comp: CbComplaintTypeUpdateComponent;
  let fixture: ComponentFixture<CbComplaintTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbComplaintTypeFormService: CbComplaintTypeFormService;
  let cbComplaintTypeService: CbComplaintTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CbComplaintTypeUpdateComponent],
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
      .overrideTemplate(CbComplaintTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbComplaintTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbComplaintTypeFormService = TestBed.inject(CbComplaintTypeFormService);
    cbComplaintTypeService = TestBed.inject(CbComplaintTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cbComplaintType: ICbComplaintType = { id: 456 };

      activatedRoute.data = of({ cbComplaintType });
      comp.ngOnInit();

      expect(comp.cbComplaintType).toEqual(cbComplaintType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintType>>();
      const cbComplaintType = { id: 123 };
      jest.spyOn(cbComplaintTypeFormService, 'getCbComplaintType').mockReturnValue(cbComplaintType);
      jest.spyOn(cbComplaintTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbComplaintType }));
      saveSubject.complete();

      // THEN
      expect(cbComplaintTypeFormService.getCbComplaintType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbComplaintTypeService.update).toHaveBeenCalledWith(expect.objectContaining(cbComplaintType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintType>>();
      const cbComplaintType = { id: 123 };
      jest.spyOn(cbComplaintTypeFormService, 'getCbComplaintType').mockReturnValue({ id: null });
      jest.spyOn(cbComplaintTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbComplaintType }));
      saveSubject.complete();

      // THEN
      expect(cbComplaintTypeFormService.getCbComplaintType).toHaveBeenCalled();
      expect(cbComplaintTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintType>>();
      const cbComplaintType = { id: 123 };
      jest.spyOn(cbComplaintTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbComplaintTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
