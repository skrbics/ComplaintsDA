import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbComplaintPhase } from '../cb-complaint-phase.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cb-complaint-phase.test-samples';

import { CbComplaintPhaseService } from './cb-complaint-phase.service';

const requireRestSample: ICbComplaintPhase = {
  ...sampleWithRequiredData,
};

describe('CbComplaintPhase Service', () => {
  let service: CbComplaintPhaseService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbComplaintPhase | ICbComplaintPhase[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbComplaintPhaseService);
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

    it('should create a CbComplaintPhase', () => {
      const cbComplaintPhase = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbComplaintPhase).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbComplaintPhase', () => {
      const cbComplaintPhase = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbComplaintPhase).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbComplaintPhase', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbComplaintPhase', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbComplaintPhase', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbComplaintPhaseToCollectionIfMissing', () => {
      it('should add a CbComplaintPhase to an empty array', () => {
        const cbComplaintPhase: ICbComplaintPhase = sampleWithRequiredData;
        expectedResult = service.addCbComplaintPhaseToCollectionIfMissing([], cbComplaintPhase);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbComplaintPhase);
      });

      it('should not add a CbComplaintPhase to an array that contains it', () => {
        const cbComplaintPhase: ICbComplaintPhase = sampleWithRequiredData;
        const cbComplaintPhaseCollection: ICbComplaintPhase[] = [
          {
            ...cbComplaintPhase,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbComplaintPhaseToCollectionIfMissing(cbComplaintPhaseCollection, cbComplaintPhase);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbComplaintPhase to an array that doesn't contain it", () => {
        const cbComplaintPhase: ICbComplaintPhase = sampleWithRequiredData;
        const cbComplaintPhaseCollection: ICbComplaintPhase[] = [sampleWithPartialData];
        expectedResult = service.addCbComplaintPhaseToCollectionIfMissing(cbComplaintPhaseCollection, cbComplaintPhase);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbComplaintPhase);
      });

      it('should add only unique CbComplaintPhase to an array', () => {
        const cbComplaintPhaseArray: ICbComplaintPhase[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cbComplaintPhaseCollection: ICbComplaintPhase[] = [sampleWithRequiredData];
        expectedResult = service.addCbComplaintPhaseToCollectionIfMissing(cbComplaintPhaseCollection, ...cbComplaintPhaseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbComplaintPhase: ICbComplaintPhase = sampleWithRequiredData;
        const cbComplaintPhase2: ICbComplaintPhase = sampleWithPartialData;
        expectedResult = service.addCbComplaintPhaseToCollectionIfMissing([], cbComplaintPhase, cbComplaintPhase2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbComplaintPhase);
        expect(expectedResult).toContain(cbComplaintPhase2);
      });

      it('should accept null and undefined values', () => {
        const cbComplaintPhase: ICbComplaintPhase = sampleWithRequiredData;
        expectedResult = service.addCbComplaintPhaseToCollectionIfMissing([], null, cbComplaintPhase, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbComplaintPhase);
      });

      it('should return initial array if no CbComplaintPhase is added', () => {
        const cbComplaintPhaseCollection: ICbComplaintPhase[] = [sampleWithRequiredData];
        expectedResult = service.addCbComplaintPhaseToCollectionIfMissing(cbComplaintPhaseCollection, undefined, null);
        expect(expectedResult).toEqual(cbComplaintPhaseCollection);
      });
    });

    describe('compareCbComplaintPhase', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbComplaintPhase(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbComplaintPhase(entity1, entity2);
        const compareResult2 = service.compareCbComplaintPhase(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbComplaintPhase(entity1, entity2);
        const compareResult2 = service.compareCbComplaintPhase(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbComplaintPhase(entity1, entity2);
        const compareResult2 = service.compareCbComplaintPhase(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
