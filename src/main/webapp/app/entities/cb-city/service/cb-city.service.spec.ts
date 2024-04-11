import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbCity } from '../cb-city.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cb-city.test-samples';

import { CbCityService } from './cb-city.service';

const requireRestSample: ICbCity = {
  ...sampleWithRequiredData,
};

describe('CbCity Service', () => {
  let service: CbCityService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbCity | ICbCity[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbCityService);
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

    it('should create a CbCity', () => {
      const cbCity = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbCity).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbCity', () => {
      const cbCity = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbCity).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbCity', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbCity', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbCity', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbCityToCollectionIfMissing', () => {
      it('should add a CbCity to an empty array', () => {
        const cbCity: ICbCity = sampleWithRequiredData;
        expectedResult = service.addCbCityToCollectionIfMissing([], cbCity);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbCity);
      });

      it('should not add a CbCity to an array that contains it', () => {
        const cbCity: ICbCity = sampleWithRequiredData;
        const cbCityCollection: ICbCity[] = [
          {
            ...cbCity,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbCityToCollectionIfMissing(cbCityCollection, cbCity);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbCity to an array that doesn't contain it", () => {
        const cbCity: ICbCity = sampleWithRequiredData;
        const cbCityCollection: ICbCity[] = [sampleWithPartialData];
        expectedResult = service.addCbCityToCollectionIfMissing(cbCityCollection, cbCity);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbCity);
      });

      it('should add only unique CbCity to an array', () => {
        const cbCityArray: ICbCity[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cbCityCollection: ICbCity[] = [sampleWithRequiredData];
        expectedResult = service.addCbCityToCollectionIfMissing(cbCityCollection, ...cbCityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbCity: ICbCity = sampleWithRequiredData;
        const cbCity2: ICbCity = sampleWithPartialData;
        expectedResult = service.addCbCityToCollectionIfMissing([], cbCity, cbCity2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbCity);
        expect(expectedResult).toContain(cbCity2);
      });

      it('should accept null and undefined values', () => {
        const cbCity: ICbCity = sampleWithRequiredData;
        expectedResult = service.addCbCityToCollectionIfMissing([], null, cbCity, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbCity);
      });

      it('should return initial array if no CbCity is added', () => {
        const cbCityCollection: ICbCity[] = [sampleWithRequiredData];
        expectedResult = service.addCbCityToCollectionIfMissing(cbCityCollection, undefined, null);
        expect(expectedResult).toEqual(cbCityCollection);
      });
    });

    describe('compareCbCity', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbCity(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbCity(entity1, entity2);
        const compareResult2 = service.compareCbCity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbCity(entity1, entity2);
        const compareResult2 = service.compareCbCity(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbCity(entity1, entity2);
        const compareResult2 = service.compareCbCity(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
