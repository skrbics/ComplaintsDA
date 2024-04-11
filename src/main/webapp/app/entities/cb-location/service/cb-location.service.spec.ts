import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbLocation } from '../cb-location.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cb-location.test-samples';

import { CbLocationService } from './cb-location.service';

const requireRestSample: ICbLocation = {
  ...sampleWithRequiredData,
};

describe('CbLocation Service', () => {
  let service: CbLocationService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbLocation | ICbLocation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbLocationService);
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

    it('should create a CbLocation', () => {
      const cbLocation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbLocation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbLocation', () => {
      const cbLocation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbLocation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbLocation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbLocation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbLocation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbLocationToCollectionIfMissing', () => {
      it('should add a CbLocation to an empty array', () => {
        const cbLocation: ICbLocation = sampleWithRequiredData;
        expectedResult = service.addCbLocationToCollectionIfMissing([], cbLocation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbLocation);
      });

      it('should not add a CbLocation to an array that contains it', () => {
        const cbLocation: ICbLocation = sampleWithRequiredData;
        const cbLocationCollection: ICbLocation[] = [
          {
            ...cbLocation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbLocationToCollectionIfMissing(cbLocationCollection, cbLocation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbLocation to an array that doesn't contain it", () => {
        const cbLocation: ICbLocation = sampleWithRequiredData;
        const cbLocationCollection: ICbLocation[] = [sampleWithPartialData];
        expectedResult = service.addCbLocationToCollectionIfMissing(cbLocationCollection, cbLocation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbLocation);
      });

      it('should add only unique CbLocation to an array', () => {
        const cbLocationArray: ICbLocation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cbLocationCollection: ICbLocation[] = [sampleWithRequiredData];
        expectedResult = service.addCbLocationToCollectionIfMissing(cbLocationCollection, ...cbLocationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbLocation: ICbLocation = sampleWithRequiredData;
        const cbLocation2: ICbLocation = sampleWithPartialData;
        expectedResult = service.addCbLocationToCollectionIfMissing([], cbLocation, cbLocation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbLocation);
        expect(expectedResult).toContain(cbLocation2);
      });

      it('should accept null and undefined values', () => {
        const cbLocation: ICbLocation = sampleWithRequiredData;
        expectedResult = service.addCbLocationToCollectionIfMissing([], null, cbLocation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbLocation);
      });

      it('should return initial array if no CbLocation is added', () => {
        const cbLocationCollection: ICbLocation[] = [sampleWithRequiredData];
        expectedResult = service.addCbLocationToCollectionIfMissing(cbLocationCollection, undefined, null);
        expect(expectedResult).toEqual(cbLocationCollection);
      });
    });

    describe('compareCbLocation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbLocation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbLocation(entity1, entity2);
        const compareResult2 = service.compareCbLocation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbLocation(entity1, entity2);
        const compareResult2 = service.compareCbLocation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbLocation(entity1, entity2);
        const compareResult2 = service.compareCbLocation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
