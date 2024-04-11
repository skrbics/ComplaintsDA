import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbCountry } from '../cb-country.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cb-country.test-samples';

import { CbCountryService } from './cb-country.service';

const requireRestSample: ICbCountry = {
  ...sampleWithRequiredData,
};

describe('CbCountry Service', () => {
  let service: CbCountryService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbCountry | ICbCountry[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbCountryService);
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

    it('should create a CbCountry', () => {
      const cbCountry = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbCountry).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbCountry', () => {
      const cbCountry = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbCountry).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbCountry', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbCountry', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbCountry', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbCountryToCollectionIfMissing', () => {
      it('should add a CbCountry to an empty array', () => {
        const cbCountry: ICbCountry = sampleWithRequiredData;
        expectedResult = service.addCbCountryToCollectionIfMissing([], cbCountry);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbCountry);
      });

      it('should not add a CbCountry to an array that contains it', () => {
        const cbCountry: ICbCountry = sampleWithRequiredData;
        const cbCountryCollection: ICbCountry[] = [
          {
            ...cbCountry,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbCountryToCollectionIfMissing(cbCountryCollection, cbCountry);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbCountry to an array that doesn't contain it", () => {
        const cbCountry: ICbCountry = sampleWithRequiredData;
        const cbCountryCollection: ICbCountry[] = [sampleWithPartialData];
        expectedResult = service.addCbCountryToCollectionIfMissing(cbCountryCollection, cbCountry);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbCountry);
      });

      it('should add only unique CbCountry to an array', () => {
        const cbCountryArray: ICbCountry[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cbCountryCollection: ICbCountry[] = [sampleWithRequiredData];
        expectedResult = service.addCbCountryToCollectionIfMissing(cbCountryCollection, ...cbCountryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbCountry: ICbCountry = sampleWithRequiredData;
        const cbCountry2: ICbCountry = sampleWithPartialData;
        expectedResult = service.addCbCountryToCollectionIfMissing([], cbCountry, cbCountry2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbCountry);
        expect(expectedResult).toContain(cbCountry2);
      });

      it('should accept null and undefined values', () => {
        const cbCountry: ICbCountry = sampleWithRequiredData;
        expectedResult = service.addCbCountryToCollectionIfMissing([], null, cbCountry, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbCountry);
      });

      it('should return initial array if no CbCountry is added', () => {
        const cbCountryCollection: ICbCountry[] = [sampleWithRequiredData];
        expectedResult = service.addCbCountryToCollectionIfMissing(cbCountryCollection, undefined, null);
        expect(expectedResult).toEqual(cbCountryCollection);
      });
    });

    describe('compareCbCountry', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbCountry(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbCountry(entity1, entity2);
        const compareResult2 = service.compareCbCountry(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbCountry(entity1, entity2);
        const compareResult2 = service.compareCbCountry(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbCountry(entity1, entity2);
        const compareResult2 = service.compareCbCountry(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
