import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbComplaintField } from '../cb-complaint-field.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cb-complaint-field.test-samples';

import { CbComplaintFieldService } from './cb-complaint-field.service';

const requireRestSample: ICbComplaintField = {
  ...sampleWithRequiredData,
};

describe('CbComplaintField Service', () => {
  let service: CbComplaintFieldService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbComplaintField | ICbComplaintField[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbComplaintFieldService);
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

    it('should create a CbComplaintField', () => {
      const cbComplaintField = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbComplaintField).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbComplaintField', () => {
      const cbComplaintField = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbComplaintField).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbComplaintField', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbComplaintField', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbComplaintField', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbComplaintFieldToCollectionIfMissing', () => {
      it('should add a CbComplaintField to an empty array', () => {
        const cbComplaintField: ICbComplaintField = sampleWithRequiredData;
        expectedResult = service.addCbComplaintFieldToCollectionIfMissing([], cbComplaintField);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbComplaintField);
      });

      it('should not add a CbComplaintField to an array that contains it', () => {
        const cbComplaintField: ICbComplaintField = sampleWithRequiredData;
        const cbComplaintFieldCollection: ICbComplaintField[] = [
          {
            ...cbComplaintField,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbComplaintFieldToCollectionIfMissing(cbComplaintFieldCollection, cbComplaintField);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbComplaintField to an array that doesn't contain it", () => {
        const cbComplaintField: ICbComplaintField = sampleWithRequiredData;
        const cbComplaintFieldCollection: ICbComplaintField[] = [sampleWithPartialData];
        expectedResult = service.addCbComplaintFieldToCollectionIfMissing(cbComplaintFieldCollection, cbComplaintField);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbComplaintField);
      });

      it('should add only unique CbComplaintField to an array', () => {
        const cbComplaintFieldArray: ICbComplaintField[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cbComplaintFieldCollection: ICbComplaintField[] = [sampleWithRequiredData];
        expectedResult = service.addCbComplaintFieldToCollectionIfMissing(cbComplaintFieldCollection, ...cbComplaintFieldArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbComplaintField: ICbComplaintField = sampleWithRequiredData;
        const cbComplaintField2: ICbComplaintField = sampleWithPartialData;
        expectedResult = service.addCbComplaintFieldToCollectionIfMissing([], cbComplaintField, cbComplaintField2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbComplaintField);
        expect(expectedResult).toContain(cbComplaintField2);
      });

      it('should accept null and undefined values', () => {
        const cbComplaintField: ICbComplaintField = sampleWithRequiredData;
        expectedResult = service.addCbComplaintFieldToCollectionIfMissing([], null, cbComplaintField, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbComplaintField);
      });

      it('should return initial array if no CbComplaintField is added', () => {
        const cbComplaintFieldCollection: ICbComplaintField[] = [sampleWithRequiredData];
        expectedResult = service.addCbComplaintFieldToCollectionIfMissing(cbComplaintFieldCollection, undefined, null);
        expect(expectedResult).toEqual(cbComplaintFieldCollection);
      });
    });

    describe('compareCbComplaintField', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbComplaintField(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbComplaintField(entity1, entity2);
        const compareResult2 = service.compareCbComplaintField(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbComplaintField(entity1, entity2);
        const compareResult2 = service.compareCbComplaintField(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbComplaintField(entity1, entity2);
        const compareResult2 = service.compareCbComplaintField(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
