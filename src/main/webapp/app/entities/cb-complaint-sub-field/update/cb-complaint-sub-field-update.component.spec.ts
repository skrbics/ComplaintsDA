import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CbComplaintSubFieldService } from '../service/cb-complaint-sub-field.service';
import { ICbComplaintSubField } from '../cb-complaint-sub-field.model';
import { CbComplaintSubFieldFormService } from './cb-complaint-sub-field-form.service';

import { CbComplaintSubFieldUpdateComponent } from './cb-complaint-sub-field-update.component';

describe('CbComplaintSubField Management Update Component', () => {
  let comp: CbComplaintSubFieldUpdateComponent;
  let fixture: ComponentFixture<CbComplaintSubFieldUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbComplaintSubFieldFormService: CbComplaintSubFieldFormService;
  let cbComplaintSubFieldService: CbComplaintSubFieldService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CbComplaintSubFieldUpdateComponent],
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
      .overrideTemplate(CbComplaintSubFieldUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbComplaintSubFieldUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbComplaintSubFieldFormService = TestBed.inject(CbComplaintSubFieldFormService);
    cbComplaintSubFieldService = TestBed.inject(CbComplaintSubFieldService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cbComplaintSubField: ICbComplaintSubField = { id: 456 };

      activatedRoute.data = of({ cbComplaintSubField });
      comp.ngOnInit();

      expect(comp.cbComplaintSubField).toEqual(cbComplaintSubField);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintSubField>>();
      const cbComplaintSubField = { id: 123 };
      jest.spyOn(cbComplaintSubFieldFormService, 'getCbComplaintSubField').mockReturnValue(cbComplaintSubField);
      jest.spyOn(cbComplaintSubFieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintSubField });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbComplaintSubField }));
      saveSubject.complete();

      // THEN
      expect(cbComplaintSubFieldFormService.getCbComplaintSubField).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbComplaintSubFieldService.update).toHaveBeenCalledWith(expect.objectContaining(cbComplaintSubField));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintSubField>>();
      const cbComplaintSubField = { id: 123 };
      jest.spyOn(cbComplaintSubFieldFormService, 'getCbComplaintSubField').mockReturnValue({ id: null });
      jest.spyOn(cbComplaintSubFieldService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintSubField: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbComplaintSubField }));
      saveSubject.complete();

      // THEN
      expect(cbComplaintSubFieldFormService.getCbComplaintSubField).toHaveBeenCalled();
      expect(cbComplaintSubFieldService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintSubField>>();
      const cbComplaintSubField = { id: 123 };
      jest.spyOn(cbComplaintSubFieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintSubField });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbComplaintSubFieldService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
