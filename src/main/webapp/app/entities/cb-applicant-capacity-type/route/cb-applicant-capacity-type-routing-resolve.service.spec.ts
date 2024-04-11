import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICbApplicantCapacityType } from '../cb-applicant-capacity-type.model';
import { CbApplicantCapacityTypeService } from '../service/cb-applicant-capacity-type.service';

import cbApplicantCapacityTypeResolve from './cb-applicant-capacity-type-routing-resolve.service';

describe('CbApplicantCapacityType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CbApplicantCapacityTypeService;
  let resultCbApplicantCapacityType: ICbApplicantCapacityType | null | undefined;

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
    service = TestBed.inject(CbApplicantCapacityTypeService);
    resultCbApplicantCapacityType = undefined;
  });

  describe('resolve', () => {
    it('should return ICbApplicantCapacityType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        cbApplicantCapacityTypeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCbApplicantCapacityType = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCbApplicantCapacityType).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        cbApplicantCapacityTypeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCbApplicantCapacityType = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCbApplicantCapacityType).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICbApplicantCapacityType>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        cbApplicantCapacityTypeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCbApplicantCapacityType = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCbApplicantCapacityType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
