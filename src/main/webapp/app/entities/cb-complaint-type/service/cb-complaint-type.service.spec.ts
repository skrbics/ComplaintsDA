import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbComplaintType } from '../cb-complaint-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cb-complaint-type.test-samples';

import { CbComplaintTypeService } from './cb-complaint-type.service';

const requireRestSample: ICbComplaintType = {
  ...sampleWithRequiredData,
};

describe('CbComplaintType Service', () => {
  let service: CbComplaintTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbComplaintType | ICbComplaintType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbComplaintTypeService);
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

    it('should create a CbComplaintType', () => {
      const cbComplaintType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbComplaintType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbComplaintType', () => {
      const cbComplaintType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbComplaintType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbComplaintType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbComplaintType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbComplaintType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbComplaintTypeToCollectionIfMissing', () => {
      it('should add a CbComplaintType to an empty array', () => {
        const cbComplaintType: ICbComplaintType = sampleWithRequiredData;
        expectedResult = service.addCbComplaintTypeToCollectionIfMissing([], cbComplaintType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbComplaintType);
      });

      it('should not add a CbComplaintType to an array that contains it', () => {
        const cbComplaintType: ICbComplaintType = sampleWithRequiredData;
        const cbComplaintTypeCollection: ICbComplaintType[] = [
          {
            ...cbComplaintType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbComplaintTypeToCollectionIfMissing(cbComplaintTypeCollection, cbComplaintType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbComplaintType to an array that doesn't contain it", () => {
        const cbComplaintType: ICbComplaintType = sampleWithRequiredData;
        const cbComplaintTypeCollection: ICbComplaintType[] = [sampleWithPartialData];
        expectedResult = service.addCbComplaintTypeToCollectionIfMissing(cbComplaintTypeCollection, cbComplaintType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbComplaintType);
      });

      it('should add only unique CbComplaintType to an array', () => {
        const cbComplaintTypeArray: ICbComplaintType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cbComplaintTypeCollection: ICbComplaintType[] = [sampleWithRequiredData];
        expectedResult = service.addCbComplaintTypeToCollectionIfMissing(cbComplaintTypeCollection, ...cbComplaintTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbComplaintType: ICbComplaintType = sampleWithRequiredData;
        const cbComplaintType2: ICbComplaintType = sampleWithPartialData;
        expectedResult = service.addCbComplaintTypeToCollectionIfMissing([], cbComplaintType, cbComplaintType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbComplaintType);
        expect(expectedResult).toContain(cbComplaintType2);
      });

      it('should accept null and undefined values', () => {
        const cbComplaintType: ICbComplaintType = sampleWithRequiredData;
        expectedResult = service.addCbComplaintTypeToCollectionIfMissing([], null, cbComplaintType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbComplaintType);
      });

      it('should return initial array if no CbComplaintType is added', () => {
        const cbComplaintTypeCollection: ICbComplaintType[] = [sampleWithRequiredData];
        expectedResult = service.addCbComplaintTypeToCollectionIfMissing(cbComplaintTypeCollection, undefined, null);
        expect(expectedResult).toEqual(cbComplaintTypeCollection);
      });
    });

    describe('compareCbComplaintType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbComplaintType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbComplaintType(entity1, entity2);
        const compareResult2 = service.compareCbComplaintType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbComplaintType(entity1, entity2);
        const compareResult2 = service.compareCbComplaintType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbComplaintType(entity1, entity2);
        const compareResult2 = service.compareCbComplaintType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
