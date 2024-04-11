import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbComplaintSubField } from '../cb-complaint-sub-field.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../cb-complaint-sub-field.test-samples';

import { CbComplaintSubFieldService } from './cb-complaint-sub-field.service';

const requireRestSample: ICbComplaintSubField = {
  ...sampleWithRequiredData,
};

describe('CbComplaintSubField Service', () => {
  let service: CbComplaintSubFieldService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbComplaintSubField | ICbComplaintSubField[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbComplaintSubFieldService);
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

    it('should create a CbComplaintSubField', () => {
      const cbComplaintSubField = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbComplaintSubField).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbComplaintSubField', () => {
      const cbComplaintSubField = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbComplaintSubField).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbComplaintSubField', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbComplaintSubField', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbComplaintSubField', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbComplaintSubFieldToCollectionIfMissing', () => {
      it('should add a CbComplaintSubField to an empty array', () => {
        const cbComplaintSubField: ICbComplaintSubField = sampleWithRequiredData;
        expectedResult = service.addCbComplaintSubFieldToCollectionIfMissing([], cbComplaintSubField);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbComplaintSubField);
      });

      it('should not add a CbComplaintSubField to an array that contains it', () => {
        const cbComplaintSubField: ICbComplaintSubField = sampleWithRequiredData;
        const cbComplaintSubFieldCollection: ICbComplaintSubField[] = [
          {
            ...cbComplaintSubField,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbComplaintSubFieldToCollectionIfMissing(cbComplaintSubFieldCollection, cbComplaintSubField);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbComplaintSubField to an array that doesn't contain it", () => {
        const cbComplaintSubField: ICbComplaintSubField = sampleWithRequiredData;
        const cbComplaintSubFieldCollection: ICbComplaintSubField[] = [sampleWithPartialData];
        expectedResult = service.addCbComplaintSubFieldToCollectionIfMissing(cbComplaintSubFieldCollection, cbComplaintSubField);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbComplaintSubField);
      });

      it('should add only unique CbComplaintSubField to an array', () => {
        const cbComplaintSubFieldArray: ICbComplaintSubField[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cbComplaintSubFieldCollection: ICbComplaintSubField[] = [sampleWithRequiredData];
        expectedResult = service.addCbComplaintSubFieldToCollectionIfMissing(cbComplaintSubFieldCollection, ...cbComplaintSubFieldArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbComplaintSubField: ICbComplaintSubField = sampleWithRequiredData;
        const cbComplaintSubField2: ICbComplaintSubField = sampleWithPartialData;
        expectedResult = service.addCbComplaintSubFieldToCollectionIfMissing([], cbComplaintSubField, cbComplaintSubField2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbComplaintSubField);
        expect(expectedResult).toContain(cbComplaintSubField2);
      });

      it('should accept null and undefined values', () => {
        const cbComplaintSubField: ICbComplaintSubField = sampleWithRequiredData;
        expectedResult = service.addCbComplaintSubFieldToCollectionIfMissing([], null, cbComplaintSubField, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbComplaintSubField);
      });

      it('should return initial array if no CbComplaintSubField is added', () => {
        const cbComplaintSubFieldCollection: ICbComplaintSubField[] = [sampleWithRequiredData];
        expectedResult = service.addCbComplaintSubFieldToCollectionIfMissing(cbComplaintSubFieldCollection, undefined, null);
        expect(expectedResult).toEqual(cbComplaintSubFieldCollection);
      });
    });

    describe('compareCbComplaintSubField', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbComplaintSubField(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbComplaintSubField(entity1, entity2);
        const compareResult2 = service.compareCbComplaintSubField(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbComplaintSubField(entity1, entity2);
        const compareResult2 = service.compareCbComplaintSubField(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbComplaintSubField(entity1, entity2);
        const compareResult2 = service.compareCbComplaintSubField(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
