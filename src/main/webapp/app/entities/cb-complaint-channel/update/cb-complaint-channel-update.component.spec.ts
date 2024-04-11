import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CbComplaintChannelService } from '../service/cb-complaint-channel.service';
import { ICbComplaintChannel } from '../cb-complaint-channel.model';
import { CbComplaintChannelFormService } from './cb-complaint-channel-form.service';

import { CbComplaintChannelUpdateComponent } from './cb-complaint-channel-update.component';

describe('CbComplaintChannel Management Update Component', () => {
  let comp: CbComplaintChannelUpdateComponent;
  let fixture: ComponentFixture<CbComplaintChannelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cbComplaintChannelFormService: CbComplaintChannelFormService;
  let cbComplaintChannelService: CbComplaintChannelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CbComplaintChannelUpdateComponent],
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
      .overrideTemplate(CbComplaintChannelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CbComplaintChannelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cbComplaintChannelFormService = TestBed.inject(CbComplaintChannelFormService);
    cbComplaintChannelService = TestBed.inject(CbComplaintChannelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cbComplaintChannel: ICbComplaintChannel = { id: 456 };

      activatedRoute.data = of({ cbComplaintChannel });
      comp.ngOnInit();

      expect(comp.cbComplaintChannel).toEqual(cbComplaintChannel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintChannel>>();
      const cbComplaintChannel = { id: 123 };
      jest.spyOn(cbComplaintChannelFormService, 'getCbComplaintChannel').mockReturnValue(cbComplaintChannel);
      jest.spyOn(cbComplaintChannelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintChannel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbComplaintChannel }));
      saveSubject.complete();

      // THEN
      expect(cbComplaintChannelFormService.getCbComplaintChannel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cbComplaintChannelService.update).toHaveBeenCalledWith(expect.objectContaining(cbComplaintChannel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintChannel>>();
      const cbComplaintChannel = { id: 123 };
      jest.spyOn(cbComplaintChannelFormService, 'getCbComplaintChannel').mockReturnValue({ id: null });
      jest.spyOn(cbComplaintChannelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintChannel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cbComplaintChannel }));
      saveSubject.complete();

      // THEN
      expect(cbComplaintChannelFormService.getCbComplaintChannel).toHaveBeenCalled();
      expect(cbComplaintChannelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICbComplaintChannel>>();
      const cbComplaintChannel = { id: 123 };
      jest.spyOn(cbComplaintChannelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cbComplaintChannel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cbComplaintChannelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
