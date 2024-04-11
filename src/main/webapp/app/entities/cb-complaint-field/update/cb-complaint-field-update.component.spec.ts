import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CbComplaintFieldService } from '../service/cb-complaint-field.service';
import { ICbComplaintField } from '../cb-complaint-field.model';
import { CbComplaintFieldFormService } from './cb-complaint-field-form.service';

import { CbComplaintFieldUpdateComponent } from './cb-complaint-field-update.component';

describe('CbComplaintField Management Update Component', () => {
  let comp: CbComplaintFieldUpdateComponent;
  let fixture: ComponentFixture<CbComplaintFieldUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbComplaintFieldFormService: CbComplaintFieldFormService;
  let cbComplaintFieldService: CbComplaintFieldService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CbComplaintFieldUpdateComponent],
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
      .overrideTemplate(CbComplaintFieldUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbComplaintFieldUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbComplaintFieldFormService = TestBed.inject(CbComplaintFieldFormService);
    cbComplaintFieldService = TestBed.inject(CbComplaintFieldService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cbComplaintField: ICbComplaintField = { id: 456 };

      activatedRoute.data = of({ cbComplaintField });
      comp.ngOnInit();

      expect(comp.cbComplaintField).toEqual(cbComplaintField);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintField>>();
      const cbComplaintField = { id: 123 };
      jest.spyOn(cbComplaintFieldFormService, 'getCbComplaintField').mockReturnValue(cbComplaintField);
      jest.spyOn(cbComplaintFieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintField });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbComplaintField }));
      saveSubject.complete();

      // THEN
      expect(cbComplaintFieldFormService.getCbComplaintField).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbComplaintFieldService.update).toHaveBeenCalledWith(expect.objectContaining(cbComplaintField));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintField>>();
      const cbComplaintField = { id: 123 };
      jest.spyOn(cbComplaintFieldFormService, 'getCbComplaintField').mockReturnValue({ id: null });
      jest.spyOn(cbComplaintFieldService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintField: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbComplaintField }));
      saveSubject.complete();

      // THEN
      expect(cbComplaintFieldFormService.getCbComplaintField).toHaveBeenCalled();
      expect(cbComplaintFieldService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintField>>();
      const cbComplaintField = { id: 123 };
      jest.spyOn(cbComplaintFieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintField });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbComplaintFieldService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
