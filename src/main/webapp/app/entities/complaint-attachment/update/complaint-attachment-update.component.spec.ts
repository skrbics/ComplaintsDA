import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IComplaint } from 'app/entities/complaint/complaint.model';
import { ComplaintService } from 'app/entities/complaint/service/complaint.service';
import { ICbAttachmentType } from 'app/entities/cb-attachment-type/cb-attachment-type.model';
import { CbAttachmentTypeService } from 'app/entities/cb-attachment-type/service/cb-attachment-type.service';
import { IComplaintAttachment } from '../complaint-attachment.model';
import { ComplaintAttachmentService } from '../service/complaint-attachment.service';
import { ComplaintAttachmentFormService } from './complaint-attachment-form.service';

import { ComplaintAttachmentUpdateComponent } from './complaint-attachment-update.component';

describe('ComplaintAttachment Management Update Component', () => {
  let comp: ComplaintAttachmentUpdateComponent;
  let fixture: ComponentFixture<ComplaintAttachmentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let complaintAttachmentFormService: ComplaintAttachmentFormService;
  let complaintAttachmentService: ComplaintAttachmentService;
  let complaintService: ComplaintService;
  let cbAttachmentTypeService: CbAttachmentTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ComplaintAttachmentUpdateComponent],
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
      .overrideTemplate(ComplaintAttachmentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ComplaintAttachmentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    complaintAttachmentFormService = TestBed.inject(ComplaintAttachmentFormService);
    complaintAttachmentService = TestBed.inject(ComplaintAttachmentService);
    complaintService = TestBed.inject(ComplaintService);
    cbAttachmentTypeService = TestBed.inject(CbAttachmentTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Complaint query and add missing value', () => {
      const complaintAttachment: IComplaintAttachment = { id: 456 };
      const complaint: IComplaint = { id: 27730 };
      complaintAttachment.complaint = complaint;

      const complaintCollection: IComplaint[] = [{ id: 16104 }];
      jest.spyOn(complaintService, 'query').mockReturnValue(of(new HttpResponse({ body: complaintCollection })));
      const additionalComplaints = [complaint];
      const expectedCollection: IComplaint[] = [...additionalComplaints, ...complaintCollection];
      jest.spyOn(complaintService, 'addComplaintToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ complaintAttachment });
      comp.ngOnInit();

      expect(complaintService.query).toHaveBeenCalled();
      expect(complaintService.addComplaintToCollectionIfMissing).toHaveBeenCalledWith(
        complaintCollection,
        ...additionalComplaints.map(expect.objectContaining),
      );
      expect(comp.complaintsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CbAttachmentType query and add missing value', () => {
      const complaintAttachment: IComplaintAttachment = { id: 456 };
      const cbAttachmentType: ICbAttachmentType = { id: 11545 };
      complaintAttachment.cbAttachmentType = cbAttachmentType;

      const cbAttachmentTypeCollection: ICbAttachmentType[] = [{ id: 7648 }];
      jest.spyOn(cbAttachmentTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: cbAttachmentTypeCollection })));
      const additionalCbAttachmentTypes = [cbAttachmentType];
      const expectedCollection: ICbAttachmentType[] = [...additionalCbAttachmentTypes, ...cbAttachmentTypeCollection];
      jest.spyOn(cbAttachmentTypeService, 'addCbAttachmentTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ complaintAttachment });
      comp.ngOnInit();

      expect(cbAttachmentTypeService.query).toHaveBeenCalled();
      expect(cbAttachmentTypeService.addCbAttachmentTypeToCollectionIfMissing).toHaveBeenCalledWith(
        cbAttachmentTypeCollection,
        ...additionalCbAttachmentTypes.map(expect.objectContaining),
      );
      expect(comp.cbAttachmentTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const complaintAttachment: IComplaintAttachment = { id: 456 };
      const complaint: IComplaint = { id: 3544 };
      complaintAttachment.complaint = complaint;
      const cbAttachmentType: ICbAttachmentType = { id: 31546 };
      complaintAttachment.cbAttachmentType = cbAttachmentType;

      activatedRoute.data = of({ complaintAttachment });
      comp.ngOnInit();

      expect(comp.complaintsSharedCollection).toContain(complaint);
      expect(comp.cbAttachmentTypesSharedCollection).toContain(cbAttachmentType);
      expect(comp.complaintAttachment).toEqual(complaintAttachment);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComplaintAttachment>>();
      const complaintAttachment = { id: 123 };
      jest.spyOn(complaintAttachmentFormService, 'getComplaintAttachment').mockReturnValue(complaintAttachment);
      jest.spyOn(complaintAttachmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ complaintAttachment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: complaintAttachment }));
      saveSubject.complete();

      // THEN
      expect(complaintAttachmentFormService.getComplaintAttachment).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(complaintAttachmentService.update).toHaveBeenCalledWith(expect.objectContaining(complaintAttachment));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComplaintAttachment>>();
      const complaintAttachment = { id: 123 };
      jest.spyOn(complaintAttachmentFormService, 'getComplaintAttachment').mockReturnValue({ id: null });
      jest.spyOn(complaintAttachmentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ complaintAttachment: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: complaintAttachment }));
      saveSubject.complete();

      // THEN
      expect(complaintAttachmentFormService.getComplaintAttachment).toHaveBeenCalled();
      expect(complaintAttachmentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComplaintAttachment>>();
      const complaintAttachment = { id: 123 };
      jest.spyOn(complaintAttachmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ complaintAttachment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(complaintAttachmentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareComplaint', () => {
      it('Should forward to complaintService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(complaintService, 'compareComplaint');
        comp.compareComplaint(entity, entity2);
        expect(complaintService.compareComplaint).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCbAttachmentType', () => {
      it('Should forward to cbAttachmentTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cbAttachmentTypeService, 'compareCbAttachmentType');
        comp.compareCbAttachmentType(entity, entity2);
        expect(cbAttachmentTypeService.compareCbAttachmentType).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
