import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbAttachmentType } from '../cb-attachment-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cb-attachment-type.test-samples';

import { CbAttachmentTypeService } from './cb-attachment-type.service';

const requireRestSample: ICbAttachmentType = {
  ...sampleWithRequiredData,
};

describe('CbAttachmentType Service', () => {
  let service: CbAttachmentTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbAttachmentType | ICbAttachmentType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbAttachmentTypeService);
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

    it('should create a CbAttachmentType', () => {
      const cbAttachmentType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbAttachmentType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbAttachmentType', () => {
      const cbAttachmentType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbAttachmentType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbAttachmentType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbAttachmentType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbAttachmentType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbAttachmentTypeToCollectionIfMissing', () => {
      it('should add a CbAttachmentType to an empty array', () => {
        const cbAttachmentType: ICbAttachmentType = sampleWithRequiredData;
        expectedResult = service.addCbAttachmentTypeToCollectionIfMissing([], cbAttachmentType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbAttachmentType);
      });

      it('should not add a CbAttachmentType to an array that contains it', () => {
        const cbAttachmentType: ICbAttachmentType = sampleWithRequiredData;
        const cbAttachmentTypeCollection: ICbAttachmentType[] = [
          {
            ...cbAttachmentType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbAttachmentTypeToCollectionIfMissing(cbAttachmentTypeCollection, cbAttachmentType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbAttachmentType to an array that doesn't contain it", () => {
        const cbAttachmentType: ICbAttachmentType = sampleWithRequiredData;
        const cbAttachmentTypeCollection: ICbAttachmentType[] = [sampleWithPartialData];
        expectedResult = service.addCbAttachmentTypeToCollectionIfMissing(cbAttachmentTypeCollection, cbAttachmentType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbAttachmentType);
      });

      it('should add only unique CbAttachmentType to an array', () => {
        const cbAttachmentTypeArray: ICbAttachmentType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cbAttachmentTypeCollection: ICbAttachmentType[] = [sampleWithRequiredData];
        expectedResult = service.addCbAttachmentTypeToCollectionIfMissing(cbAttachmentTypeCollection, ...cbAttachmentTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbAttachmentType: ICbAttachmentType = sampleWithRequiredData;
        const cbAttachmentType2: ICbAttachmentType = sampleWithPartialData;
        expectedResult = service.addCbAttachmentTypeToCollectionIfMissing([], cbAttachmentType, cbAttachmentType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbAttachmentType);
        expect(expectedResult).toContain(cbAttachmentType2);
      });

      it('should accept null and undefined values', () => {
        const cbAttachmentType: ICbAttachmentType = sampleWithRequiredData;
        expectedResult = service.addCbAttachmentTypeToCollectionIfMissing([], null, cbAttachmentType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbAttachmentType);
      });

      it('should return initial array if no CbAttachmentType is added', () => {
        const cbAttachmentTypeCollection: ICbAttachmentType[] = [sampleWithRequiredData];
        expectedResult = service.addCbAttachmentTypeToCollectionIfMissing(cbAttachmentTypeCollection, undefined, null);
        expect(expectedResult).toEqual(cbAttachmentTypeCollection);
      });
    });

    describe('compareCbAttachmentType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbAttachmentType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbAttachmentType(entity1, entity2);
        const compareResult2 = service.compareCbAttachmentType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbAttachmentType(entity1, entity2);
        const compareResult2 = service.compareCbAttachmentType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbAttachmentType(entity1, entity2);
        const compareResult2 = service.compareCbAttachmentType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
