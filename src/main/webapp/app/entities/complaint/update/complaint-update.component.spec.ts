import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IApplicant } from 'app/entities/applicant/applicant.model';
import { ApplicantService } from 'app/entities/applicant/service/applicant.service';
import { ICbComplaintField } from 'app/entities/cb-complaint-field/cb-complaint-field.model';
import { CbComplaintFieldService } from 'app/entities/cb-complaint-field/service/cb-complaint-field.service';
import { ICbComplaintSubField } from 'app/entities/cb-complaint-sub-field/cb-complaint-sub-field.model';
import { CbComplaintSubFieldService } from 'app/entities/cb-complaint-sub-field/service/cb-complaint-sub-field.service';
import { ICbComplaintType } from 'app/entities/cb-complaint-type/cb-complaint-type.model';
import { CbComplaintTypeService } from 'app/entities/cb-complaint-type/service/cb-complaint-type.service';
import { ICbComplaintChannel } from 'app/entities/cb-complaint-channel/cb-complaint-channel.model';
import { CbComplaintChannelService } from 'app/entities/cb-complaint-channel/service/cb-complaint-channel.service';
import { ICbApplicantCapacityType } from 'app/entities/cb-applicant-capacity-type/cb-applicant-capacity-type.model';
import { CbApplicantCapacityTypeService } from 'app/entities/cb-applicant-capacity-type/service/cb-applicant-capacity-type.service';
import { ICbComplaintPhase } from 'app/entities/cb-complaint-phase/cb-complaint-phase.model';
import { CbComplaintPhaseService } from 'app/entities/cb-complaint-phase/service/cb-complaint-phase.service';
import { IComplaint } from '../complaint.model';
import { ComplaintService } from '../service/complaint.service';
import { ComplaintFormService } from './complaint-form.service';

import { ComplaintUpdateComponent } from './complaint-update.component';

describe('Complaint Management Update Component', () => {
  let comp: ComplaintUpdateComponent;
  let fixture: ComponentFixture<ComplaintUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let complaintFormService: ComplaintFormService;
  let complaintService: ComplaintService;
  let applicantService: ApplicantService;
  let cbComplaintFieldService: CbComplaintFieldService;
  let cbComplaintSubFieldService: CbComplaintSubFieldService;
  let cbComplaintTypeService: CbComplaintTypeService;
  let cbComplaintChannelService: CbComplaintChannelService;
  let cbApplicantCapacityTypeService: CbApplicantCapacityTypeService;
  let cbComplaintPhaseService: CbComplaintPhaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ComplaintUpdateComponent],
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
      .overrideTemplate(ComplaintUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ComplaintUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    complaintFormService = TestBed.inject(ComplaintFormService);
    complaintService = TestBed.inject(ComplaintService);
    applicantService = TestBed.inject(ApplicantService);
    cbComplaintFieldService = TestBed.inject(CbComplaintFieldService);
    cbComplaintSubFieldService = TestBed.inject(CbComplaintSubFieldService);
    cbComplaintTypeService = TestBed.inject(CbComplaintTypeService);
    cbComplaintChannelService = TestBed.inject(CbComplaintChannelService);
    cbApplicantCapacityTypeService = TestBed.inject(CbApplicantCapacityTypeService);
    cbComplaintPhaseService = TestBed.inject(CbComplaintPhaseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Applicant query and add missing value', () => {
      const complaint: IComplaint = { id: 456 };
      const applicant: IApplicant = { id: 24062 };
      complaint.applicant = applicant;

      const applicantCollection: IApplicant[] = [{ id: 16871 }];
      jest.spyOn(applicantService, 'query').mockReturnValue(of(new HttpResponse({ body: applicantCollection })));
      const additionalApplicants = [applicant];
      const expectedCollection: IApplicant[] = [...additionalApplicants, ...applicantCollection];
      jest.spyOn(applicantService, 'addApplicantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ complaint });
      comp.ngOnInit();

      expect(applicantService.query).toHaveBeenCalled();
      expect(applicantService.addApplicantToCollectionIfMissing).toHaveBeenCalledWith(
        applicantCollection,
        ...additionalApplicants.map(expect.objectContaining),
      );
      expect(comp.applicantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CbComplaintField query and add missing value', () => {
      const complaint: IComplaint = { id: 456 };
      const cbComplaintField: ICbComplaintField = { id: 14996 };
      complaint.cbComplaintField = cbComplaintField;

      const cbComplaintFieldCollection: ICbComplaintField[] = [{ id: 10020 }];
      jest.spyOn(cbComplaintFieldService, 'query').mockReturnValue(of(new HttpResponse({ body: cbComplaintFieldCollection })));
      const additionalCbComplaintFields = [cbComplaintField];
      const expectedCollection: ICbComplaintField[] = [...additionalCbComplaintFields, ...cbComplaintFieldCollection];
      jest.spyOn(cbComplaintFieldService, 'addCbComplaintFieldToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ complaint });
      comp.ngOnInit();

      expect(cbComplaintFieldService.query).toHaveBeenCalled();
      expect(cbComplaintFieldService.addCbComplaintFieldToCollectionIfMissing).toHaveBeenCalledWith(
        cbComplaintFieldCollection,
        ...additionalCbComplaintFields.map(expect.objectContaining),
      );
      expect(comp.cbComplaintFieldsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CbComplaintSubField query and add missing value', () => {
      const complaint: IComplaint = { id: 456 };
      const cbComplaintSubField: ICbComplaintSubField = { id: 17279 };
      complaint.cbComplaintSubField = cbComplaintSubField;

      const cbComplaintSubFieldCollection: ICbComplaintSubField[] = [{ id: 5830 }];
      jest.spyOn(cbComplaintSubFieldService, 'query').mockReturnValue(of(new HttpResponse({ body: cbComplaintSubFieldCollection })));
      const additionalCbComplaintSubFields = [cbComplaintSubField];
      const expectedCollection: ICbComplaintSubField[] = [...additionalCbComplaintSubFields, ...cbComplaintSubFieldCollection];
      jest.spyOn(cbComplaintSubFieldService, 'addCbComplaintSubFieldToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ complaint });
      comp.ngOnInit();

      expect(cbComplaintSubFieldService.query).toHaveBeenCalled();
      expect(cbComplaintSubFieldService.addCbComplaintSubFieldToCollectionIfMissing).toHaveBeenCalledWith(
        cbComplaintSubFieldCollection,
        ...additionalCbComplaintSubFields.map(expect.objectContaining),
      );
      expect(comp.cbComplaintSubFieldsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CbComplaintType query and add missing value', () => {
      const complaint: IComplaint = { id: 456 };
      const cbComplaintType: ICbComplaintType = { id: 13509 };
      complaint.cbComplaintType = cbComplaintType;

      const cbComplaintTypeCollection: ICbComplaintType[] = [{ id: 17181 }];
      jest.spyOn(cbComplaintTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: cbComplaintTypeCollection })));
      const additionalCbComplaintTypes = [cbComplaintType];
      const expectedCollection: ICbComplaintType[] = [...additionalCbComplaintTypes, ...cbComplaintTypeCollection];
      jest.spyOn(cbComplaintTypeService, 'addCbComplaintTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ complaint });
      comp.ngOnInit();

      expect(cbComplaintTypeService.query).toHaveBeenCalled();
      expect(cbComplaintTypeService.addCbComplaintTypeToCollectionIfMissing).toHaveBeenCalledWith(
        cbComplaintTypeCollection,
        ...additionalCbComplaintTypes.map(expect.objectContaining),
      );
      expect(comp.cbComplaintTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CbComplaintChannel query and add missing value', () => {
      const complaint: IComplaint = { id: 456 };
      const cbComplaintChannel: ICbComplaintChannel = { id: 10342 };
      complaint.cbComplaintChannel = cbComplaintChannel;

      const cbComplaintChannelCollection: ICbComplaintChannel[] = [{ id: 23854 }];
      jest.spyOn(cbComplaintChannelService, 'query').mockReturnValue(of(new HttpResponse({ body: cbComplaintChannelCollection })));
      const additionalCbComplaintChannels = [cbComplaintChannel];
      const expectedCollection: ICbComplaintChannel[] = [...additionalCbComplaintChannels, ...cbComplaintChannelCollection];
      jest.spyOn(cbComplaintChannelService, 'addCbComplaintChannelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ complaint });
      comp.ngOnInit();

      expect(cbComplaintChannelService.query).toHaveBeenCalled();
      expect(cbComplaintChannelService.addCbComplaintChannelToCollectionIfMissing).toHaveBeenCalledWith(
        cbComplaintChannelCollection,
        ...additionalCbComplaintChannels.map(expect.objectContaining),
      );
      expect(comp.cbComplaintChannelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CbApplicantCapacityType query and add missing value', () => {
      const complaint: IComplaint = { id: 456 };
      const cbApplicantCapacityType: ICbApplicantCapacityType = { id: 21965 };
      complaint.cbApplicantCapacityType = cbApplicantCapacityType;

      const cbApplicantCapacityTypeCollection: ICbApplicantCapacityType[] = [{ id: 19447 }];
      jest
        .spyOn(cbApplicantCapacityTypeService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: cbApplicantCapacityTypeCollection })));
      const additionalCbApplicantCapacityTypes = [cbApplicantCapacityType];
      const expectedCollection: ICbApplicantCapacityType[] = [...additionalCbApplicantCapacityTypes, ...cbApplicantCapacityTypeCollection];
      jest.spyOn(cbApplicantCapacityTypeService, 'addCbApplicantCapacityTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ complaint });
      comp.ngOnInit();

      expect(cbApplicantCapacityTypeService.query).toHaveBeenCalled();
      expect(cbApplicantCapacityTypeService.addCbApplicantCapacityTypeToCollectionIfMissing).toHaveBeenCalledWith(
        cbApplicantCapacityTypeCollection,
        ...additionalCbApplicantCapacityTypes.map(expect.objectContaining),
      );
      expect(comp.cbApplicantCapacityTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CbComplaintPhase query and add missing value', () => {
      const complaint: IComplaint = { id: 456 };
      const cbComplaintPhase: ICbComplaintPhase = { id: 4728 };
      complaint.cbComplaintPhase = cbComplaintPhase;

      const cbComplaintPhaseCollection: ICbComplaintPhase[] = [{ id: 31505 }];
      jest.spyOn(cbComplaintPhaseService, 'query').mockReturnValue(of(new HttpResponse({ body: cbComplaintPhaseCollection })));
      const additionalCbComplaintPhases = [cbComplaintPhase];
      const expectedCollection: ICbComplaintPhase[] = [...additionalCbComplaintPhases, ...cbComplaintPhaseCollection];
      jest.spyOn(cbComplaintPhaseService, 'addCbComplaintPhaseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ complaint });
      comp.ngOnInit();

      expect(cbComplaintPhaseService.query).toHaveBeenCalled();
      expect(cbComplaintPhaseService.addCbComplaintPhaseToCollectionIfMissing).toHaveBeenCalledWith(
        cbComplaintPhaseCollection,
        ...additionalCbComplaintPhases.map(expect.objectContaining),
      );
      expect(comp.cbComplaintPhasesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const complaint: IComplaint = { id: 456 };
      const applicant: IApplicant = { id: 4196 };
      complaint.applicant = applicant;
      const cbComplaintField: ICbComplaintField = { id: 10345 };
      complaint.cbComplaintField = cbComplaintField;
      const cbComplaintSubField: ICbComplaintSubField = { id: 28506 };
      complaint.cbComplaintSubField = cbComplaintSubField;
      const cbComplaintType: ICbComplaintType = { id: 26178 };
      complaint.cbComplaintType = cbComplaintType;
      const cbComplaintChannel: ICbComplaintChannel = { id: 17882 };
      complaint.cbComplaintChannel = cbComplaintChannel;
      const cbApplicantCapacityType: ICbApplicantCapacityType = { id: 4579 };
      complaint.cbApplicantCapacityType = cbApplicantCapacityType;
      const cbComplaintPhase: ICbComplaintPhase = { id: 9303 };
      complaint.cbComplaintPhase = cbComplaintPhase;

      activatedRoute.data = of({ complaint });
      comp.ngOnInit();

      expect(comp.applicantsSharedCollection).toContain(applicant);
      expect(comp.cbComplaintFieldsSharedCollection).toContain(cbComplaintField);
      expect(comp.cbComplaintSubFieldsSharedCollection).toContain(cbComplaintSubField);
      expect(comp.cbComplaintTypesSharedCollection).toContain(cbComplaintType);
      expect(comp.cbComplaintChannelsSharedCollection).toContain(cbComplaintChannel);
      expect(comp.cbApplicantCapacityTypesSharedCollection).toContain(cbApplicantCapacityType);
      expect(comp.cbComplaintPhasesSharedCollection).toContain(cbComplaintPhase);
      expect(comp.complaint).toEqual(complaint);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComplaint>>();
      const complaint = { id: 123 };
      jest.spyOn(complaintFormService, 'getComplaint').mockReturnValue(complaint);
      jest.spyOn(complaintService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ complaint });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: complaint }));
      saveSubject.complete();

      // THEN
      expect(complaintFormService.getComplaint).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(complaintService.update).toHaveBeenCalledWith(expect.objectContaining(complaint));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComplaint>>();
      const complaint = { id: 123 };
      jest.spyOn(complaintFormService, 'getComplaint').mockReturnValue({ id: null });
      jest.spyOn(complaintService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ complaint: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: complaint }));
      saveSubject.complete();

      // THEN
      expect(complaintFormService.getComplaint).toHaveBeenCalled();
      expect(complaintService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComplaint>>();
      const complaint = { id: 123 };
      jest.spyOn(complaintService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ complaint });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(complaintService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareApplicant', () => {
      it('Should forward to applicantService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(applicantService, 'compareApplicant');
        comp.compareApplicant(entity, entity2);
        expect(applicantService.compareApplicant).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCbComplaintField', () => {
      it('Should forward to cbComplaintFieldService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cbComplaintFieldService, 'compareCbComplaintField');
        comp.compareCbComplaintField(entity, entity2);
        expect(cbComplaintFieldService.compareCbComplaintField).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCbComplaintSubField', () => {
      it('Should forward to cbComplaintSubFieldService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cbComplaintSubFieldService, 'compareCbComplaintSubField');
        comp.compareCbComplaintSubField(entity, entity2);
        expect(cbComplaintSubFieldService.compareCbComplaintSubField).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCbComplaintType', () => {
      it('Should forward to cbComplaintTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cbComplaintTypeService, 'compareCbComplaintType');
        comp.compareCbComplaintType(entity, entity2);
        expect(cbComplaintTypeService.compareCbComplaintType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCbComplaintChannel', () => {
      it('Should forward to cbComplaintChannelService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cbComplaintChannelService, 'compareCbComplaintChannel');
        comp.compareCbComplaintChannel(entity, entity2);
        expect(cbComplaintChannelService.compareCbComplaintChannel).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCbApplicantCapacityType', () => {
      it('Should forward to cbApplicantCapacityTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cbApplicantCapacityTypeService, 'compareCbApplicantCapacityType');
        comp.compareCbApplicantCapacityType(entity, entity2);
        expect(cbApplicantCapacityTypeService.compareCbApplicantCapacityType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCbComplaintPhase', () => {
      it('Should forward to cbComplaintPhaseService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cbComplaintPhaseService, 'compareCbComplaintPhase');
        comp.compareCbComplaintPhase(entity, entity2);
        expect(cbComplaintPhaseService.compareCbComplaintPhase).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
