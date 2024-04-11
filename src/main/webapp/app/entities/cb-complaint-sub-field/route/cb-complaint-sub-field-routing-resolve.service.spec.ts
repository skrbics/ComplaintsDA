import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICbComplaintSubField } from '../cb-complaint-sub-field.model';
import { CbComplaintSubFieldService } from '../service/cb-complaint-sub-field.service';

import cbComplaintSubFieldResolve from './cb-complaint-sub-field-routing-resolve.service';

describe('CbComplaintSubField routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CbComplaintSubFieldService;
  let resultCbComplaintSubField: ICbComplaintSubField | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(CbComplaintSubFieldService);
    resultCbComplaintSubField = undefined;
  });

  describe('resolve', () => {
    it('should return ICbComplaintSubField returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        cbComplaintSubFieldResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCbComplaintSubField = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCbComplaintSubField).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        cbComplaintSubFieldResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCbComplaintSubField = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCbComplaintSubField).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICbComplaintSubField>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        cbComplaintSubFieldResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCbComplaintSubField = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCbComplaintSubField).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
