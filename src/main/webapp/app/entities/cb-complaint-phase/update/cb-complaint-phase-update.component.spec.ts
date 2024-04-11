import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CbComplaintPhaseService } from '../service/cb-complaint-phase.service';
import { ICbComplaintPhase } from '../cb-complaint-phase.model';
import { CbComplaintPhaseFormService } from './cb-complaint-phase-form.service';

import { CbComplaintPhaseUpdateComponent } from './cb-complaint-phase-update.component';

describe('CbComplaintPhase Management Update Component', () => {
  let comp: CbComplaintPhaseUpdateComponent;
  let fixture: ComponentFixture<CbComplaintPhaseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbComplaintPhaseFormService: CbComplaintPhaseFormService;
  let cbComplaintPhaseService: CbComplaintPhaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CbComplaintPhaseUpdateComponent],
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
      .overrideTemplate(CbComplaintPhaseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbComplaintPhaseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbComplaintPhaseFormService = TestBed.inject(CbComplaintPhaseFormService);
    cbComplaintPhaseService = TestBed.inject(CbComplaintPhaseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cbComplaintPhase: ICbComplaintPhase = { id: 456 };

      activatedRoute.data = of({ cbComplaintPhase });
      comp.ngOnInit();

      expect(comp.cbComplaintPhase).toEqual(cbComplaintPhase);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintPhase>>();
      const cbComplaintPhase = { id: 123 };
      jest.spyOn(cbComplaintPhaseFormService, 'getCbComplaintPhase').mockReturnValue(cbComplaintPhase);
      jest.spyOn(cbComplaintPhaseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintPhase });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbComplaintPhase }));
      saveSubject.complete();

      // THEN
      expect(cbComplaintPhaseFormService.getCbComplaintPhase).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbComplaintPhaseService.update).toHaveBeenCalledWith(expect.objectContaining(cbComplaintPhase));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintPhase>>();
      const cbComplaintPhase = { id: 123 };
      jest.spyOn(cbComplaintPhaseFormService, 'getCbComplaintPhase').mockReturnValue({ id: null });
      jest.spyOn(cbComplaintPhaseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintPhase: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbComplaintPhase }));
      saveSubject.complete();

      // THEN
      expect(cbComplaintPhaseFormService.getCbComplaintPhase).toHaveBeenCalled();
      expect(cbComplaintPhaseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintPhase>>();
      const cbComplaintPhase = { id: 123 };
      jest.spyOn(cbComplaintPhaseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintPhase });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbComplaintPhaseService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
