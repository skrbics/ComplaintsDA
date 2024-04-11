import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICbComplaintChannel } from '../cb-complaint-channel.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cb-complaint-channel.test-samples';

import { CbComplaintChannelService } from './cb-complaint-channel.service';

const requireRestSample: ICbComplaintChannel = {
  ...sampleWithRequiredData,
};

describe('CbComplaintChannel Service', () => {
  let service: CbComplaintChannelService;
  let httpMock: HttpTestingController;
  let expectedResult: ICbComplaintChannel | ICbComplaintChannel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CbComplaintChannelService);
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

    it('should create a CbComplaintChannel', () => {
      const cbComplaintChannel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cbComplaintChannel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CbComplaintChannel', () => {
      const cbComplaintChannel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cbComplaintChannel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CbComplaintChannel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CbComplaintChannel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CbComplaintChannel', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCbComplaintChannelToCollectionIfMissing', () => {
      it('should add a CbComplaintChannel to an empty array', () => {
        const cbComplaintChannel: ICbComplaintChannel = sampleWithRequiredData;
        expectedResult = service.addCbComplaintChannelToCollectionIfMissing([], cbComplaintChannel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbComplaintChannel);
      });

      it('should not add a CbComplaintChannel to an array that contains it', () => {
        const cbComplaintChannel: ICbComplaintChannel = sampleWithRequiredData;
        const cbComplaintChannelCollection: ICbComplaintChannel[] = [
          {
            ...cbComplaintChannel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCbComplaintChannelToCollectionIfMissing(cbComplaintChannelCollection, cbComplaintChannel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CbComplaintChannel to an array that doesn't contain it", () => {
        const cbComplaintChannel: ICbComplaintChannel = sampleWithRequiredData;
        const cbComplaintChannelCollection: ICbComplaintChannel[] = [sampleWithPartialData];
        expectedResult = service.addCbComplaintChannelToCollectionIfMissing(cbComplaintChannelCollection, cbComplaintChannel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbComplaintChannel);
      });

      it('should add only unique CbComplaintChannel to an array', () => {
        const cbComplaintChannelArray: ICbComplaintChannel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cbComplaintChannelCollection: ICbComplaintChannel[] = [sampleWithRequiredData];
        expectedResult = service.addCbComplaintChannelToCollectionIfMissing(cbComplaintChannelCollection, ...cbComplaintChannelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cbComplaintChannel: ICbComplaintChannel = sampleWithRequiredData;
        const cbComplaintChannel2: ICbComplaintChannel = sampleWithPartialData;
        expectedResult = service.addCbComplaintChannelToCollectionIfMissing([], cbComplaintChannel, cbComplaintChannel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cbComplaintChannel);
        expect(expectedResult).toContain(cbComplaintChannel2);
      });

      it('should accept null and undefined values', () => {
        const cbComplaintChannel: ICbComplaintChannel = sampleWithRequiredData;
        expectedResult = service.addCbComplaintChannelToCollectionIfMissing([], null, cbComplaintChannel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cbComplaintChannel);
      });

      it('should return initial array if no CbComplaintChannel is added', () => {
        const cbComplaintChannelCollection: ICbComplaintChannel[] = [sampleWithRequiredData];
        expectedResult = service.addCbComplaintChannelToCollectionIfMissing(cbComplaintChannelCollection, undefined, null);
        expect(expectedResult).toEqual(cbComplaintChannelCollection);
      });
    });

    describe('compareCbComplaintChannel', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCbComplaintChannel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCbComplaintChannel(entity1, entity2);
        const compareResult2 = service.compareCbComplaintChannel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCbComplaintChannel(entity1, entity2);
        const compareResult2 = service.compareCbComplaintChannel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCbComplaintChannel(entity1, entity2);
        const compareResult2 = service.compareCbComplaintChannel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
