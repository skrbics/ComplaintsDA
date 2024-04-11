import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IComplaintAttachment } from '../complaint-attachment.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../complaint-attachment.test-samples';

import { ComplaintAttachmentService } from './complaint-attachment.service';

const requireRestSample: IComplaintAttachment = {
  ...sampleWithRequiredData,
};

describe('ComplaintAttachment Service', () => {
  let service: ComplaintAttachmentService;
  let httpMock: HttpTestingController;
  let expectedResult: IComplaintAttachment | IComplaintAttachment[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ComplaintAttachmentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ComplaintAttachment', () => {
      const complaintAttachment = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(complaintAttachment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ComplaintAttachment', () => {
      const complaintAttachment = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(complaintAttachment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ComplaintAttachment', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ComplaintAttachment', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ComplaintAttachment', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addComplaintAttachmentToCollectionIfMissing', () => {
      it('should add a ComplaintAttachment to an empty array', () => {
        const complaintAttachment: IComplaintAttachment = sampleWithRequiredData;
        expectedResult = service.addComplaintAttachmentToCollectionIfMissing([], complaintAttachment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(complaintAttachment);
      });

      it('should not add a ComplaintAttachment to an array that contains it', () => {
        const complaintAttachment: IComplaintAttachment = sampleWithRequiredData;
        const complaintAttachmentCollection: IComplaintAttachment[] = [
          {
            ...complaintAttachment,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addComplaintAttachmentToCollectionIfMissing(complaintAttachmentCollection, complaintAttachment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ComplaintAttachment to an array that doesn't contain it", () => {
        const complaintAttachment: IComplaintAttachment = sampleWithRequiredData;
        const complaintAttachmentCollection: IComplaintAttachment[] = [sampleWithPartialData];
        expectedResult = service.addComplaintAttachmentToCollectionIfMissing(complaintAttachmentCollection, complaintAttachment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(complaintAttachment);
      });

      it('should add only unique ComplaintAttachment to an array', () => {
        const complaintAttachmentArray: IComplaintAttachment[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const complaintAttachmentCollection: IComplaintAttachment[] = [sampleWithRequiredData];
        expectedResult = service.addComplaintAttachmentToCollectionIfMissing(complaintAttachmentCollection, ...complaintAttachmentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const complaintAttachment: IComplaintAttachment = sampleWithRequiredData;
        const complaintAttachment2: IComplaintAttachment = sampleWithPartialData;
        expectedResult = service.addComplaintAttachmentToCollectionIfMissing([], complaintAttachment, complaintAttachment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(complaintAttachment);
        expect(expectedResult).toContain(complaintAttachment2);
      });

      it('should accept null and undefined values', () => {
        const complaintAttachment: IComplaintAttachment = sampleWithRequiredData;
        expectedResult = service.addComplaintAttachmentToCollectionIfMissing([], null, complaintAttachment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(complaintAttachment);
      });

      it('should return initial array if no ComplaintAttachment is added', () => {
        const complaintAttachmentCollection: IComplaintAttachment[] = [sampleWithRequiredData];
        expectedResult = service.addComplaintAttachmentToCollectionIfMissing(complaintAttachmentCollection, undefined, null);
        expect(expectedResult).toEqual(complaintAttachmentCollection);
      });
    });

    describe('compareComplaintAttachment', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareComplaintAttachment(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareComplaintAttachment(entity1, entity2);
        const compareResult2 = service.compareComplaintAttachment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareComplaintAttachment(entity1, entity2);
        const compareResult2 = service.compareComplaintAttachment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareComplaintAttachment(entity1, entity2);
        const compareResult2 = service.compareComplaintAttachment(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
