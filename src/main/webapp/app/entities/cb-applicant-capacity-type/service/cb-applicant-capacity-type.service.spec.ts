import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbApplicantCapacityType } from '../cb-applicant-capacity-type.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../cb-applicant-capacity-type.test-samples';

import { CbApplicantCapacityTypeService } from './cb-applicant-capacity-type.service';

const requireRestSample: ICbApplicantCapacityType = {
  ...sampleWithRequiredData,
};

describe('CbApplicantCapacityType Service', () => {
  let service: CbApplicantCapacityTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbApplicantCapacityType | ICbApplicantCapacityType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbApplicantCapacityTypeService);
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

    it('should create a CbApplicantCapacityType', () => {
      const cbApplicantCapacityType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbApplicantCapacityType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbApplicantCapacityType', () => {
      const cbApplicantCapacityType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbApplicantCapacityType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbApplicantCapacityType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbApplicantCapacityType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbApplicantCapacityType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbApplicantCapacityTypeToCollectionIfMissing', () => {
      it('should add a CbApplicantCapacityType to an empty array', () => {
        const cbApplicantCapacityType: ICbApplicantCapacityType = sampleWithRequiredData;
        expectedResult = service.addCbApplicantCapacityTypeToCollectionIfMissing([], cbApplicantCapacityType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbApplicantCapacityType);
      });

      it('should not add a CbApplicantCapacityType to an array that contains it', () => {
        const cbApplicantCapacityType: ICbApplicantCapacityType = sampleWithRequiredData;
        const cbApplicantCapacityTypeCollection: ICbApplicantCapacityType[] = [
          {
            ...cbApplicantCapacityType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbApplicantCapacityTypeToCollectionIfMissing(
          cbApplicantCapacityTypeCollection,
          cbApplicantCapacityType,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbApplicantCapacityType to an array that doesn't contain it", () => {
        const cbApplicantCapacityType: ICbApplicantCapacityType = sampleWithRequiredData;
        const cbApplicantCapacityTypeCollection: ICbApplicantCapacityType[] = [sampleWithPartialData];
        expectedResult = service.addCbApplicantCapacityTypeToCollectionIfMissing(
          cbApplicantCapacityTypeCollection,
          cbApplicantCapacityType,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbApplicantCapacityType);
      });

      it('should add only unique CbApplicantCapacityType to an array', () => {
        const cbApplicantCapacityTypeArray: ICbApplicantCapacityType[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const cbApplicantCapacityTypeCollection: ICbApplicantCapacityType[] = [sampleWithRequiredData];
        expectedResult = service.addCbApplicantCapacityTypeToCollectionIfMissing(
          cbApplicantCapacityTypeCollection,
          ...cbApplicantCapacityTypeArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbApplicantCapacityType: ICbApplicantCapacityType = sampleWithRequiredData;
        const cbApplicantCapacityType2: ICbApplicantCapacityType = sampleWithPartialData;
        expectedResult = service.addCbApplicantCapacityTypeToCollectionIfMissing([], cbApplicantCapacityType, cbApplicantCapacityType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbApplicantCapacityType);
        expect(expectedResult).toContain(cbApplicantCapacityType2);
      });

      it('should accept null and undefined values', () => {
        const cbApplicantCapacityType: ICbApplicantCapacityType = sampleWithRequiredData;
        expectedResult = service.addCbApplicantCapacityTypeToCollectionIfMissing([], null, cbApplicantCapacityType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbApplicantCapacityType);
      });

      it('should return initial array if no CbApplicantCapacityType is added', () => {
        const cbApplicantCapacityTypeCollection: ICbApplicantCapacityType[] = [sampleWithRequiredData];
        expectedResult = service.addCbApplicantCapacityTypeToCollectionIfMissing(cbApplicantCapacityTypeCollection, undefined, null);
        expect(expectedResult).toEqual(cbApplicantCapacityTypeCollection);
      });
    });

    describe('compareCbApplicantCapacityType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbApplicantCapacityType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbApplicantCapacityType(entity1, entity2);
        const compareResult2 = service.compareCbApplicantCapacityType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbApplicantCapacityType(entity1, entity2);
        const compareResult2 = service.compareCbApplicantCapacityType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbApplicantCapacityType(entity1, entity2);
        const compareResult2 = service.compareCbApplicantCapacityType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
