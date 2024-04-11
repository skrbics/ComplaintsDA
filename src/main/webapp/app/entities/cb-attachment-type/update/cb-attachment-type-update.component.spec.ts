import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CbAttachmentTypeService } from '../service/cb-attachment-type.service';
import { ICbAttachmentType } from '../cb-attachment-type.model';
import { CbAttachmentTypeFormService } from './cb-attachment-type-form.service';

import { CbAttachmentTypeUpdateComponent } from './cb-attachment-type-update.component';

describe('CbAttachmentType Management Update Component', () => {
  let comp: CbAttachmentTypeUpdateComponent;
  let fixture: ComponentFixture<CbAttachmentTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbAttachmentTypeFormService: CbAttachmentTypeFormService;
  let cbAttachmentTypeService: CbAttachmentTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CbAttachmentTypeUpdateComponent],
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
      .overrideTemplate(CbAttachmentTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbAttachmentTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbAttachmentTypeFormService = TestBed.inject(CbAttachmentTypeFormService);
    cbAttachmentTypeService = TestBed.inject(CbAttachmentTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cbAttachmentType: ICbAttachmentType = { id: 456 };

      activatedRoute.data = of({ cbAttachmentType });
      comp.ngOnInit();

      expect(comp.cbAttachmentType).toEqual(cbAttachmentType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbAttachmentType>>();
      const cbAttachmentType = { id: 123 };
      jest.spyOn(cbAttachmentTypeFormService, 'getCbAttachmentType').mockReturnValue(cbAttachmentType);
      jest.spyOn(cbAttachmentTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbAttachmentType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbAttachmentType }));
      saveSubject.complete();

      // THEN
      expect(cbAttachmentTypeFormService.getCbAttachmentType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbAttachmentTypeService.update).toHaveBeenCalledWith(expect.objectContaining(cbAttachmentType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbAttachmentType>>();
      const cbAttachmentType = { id: 123 };
      jest.spyOn(cbAttachmentTypeFormService, 'getCbAttachmentType').mockReturnValue({ id: null });
      jest.spyOn(cbAttachmentTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbAttachmentType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbAttachmentType }));
      saveSubject.complete();

      // THEN
      expect(cbAttachmentTypeFormService.getCbAttachmentType).toHaveBeenCalled();
      expect(cbAttachmentTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbAttachmentType>>();
      const cbAttachmentType = { id: 123 };
      jest.spyOn(cbAttachmentTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbAttachmentType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbAttachmentTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
