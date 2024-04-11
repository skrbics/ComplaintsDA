import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICbComplaintChannel } from '../cb-complaint-channel.model';
import { CbComplaintChannelService } from '../service/cb-complaint-channel.service';

import cbComplaintChannelResolve from './cb-complaint-channel-routing-resolve.service';

describe('CbComplaintChannel routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CbComplaintChannelService;
  let resultCbComplaintChannel: ICbComplaintChannel | null | undefined;

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
    service = TestBed.inject(CbComplaintChannelService);
    resultCbComplaintChannel = undefined;
  });

  describe('resolve', () => {
    it('should return ICbComplaintChannel returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        cbComplaintChannelResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCbComplaintChannel = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCbComplaintChannel).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        cbComplaintChannelResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCbComplaintChannel = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCbComplaintChannel).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICbComplaintChannel>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        cbComplaintChannelResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCbComplaintChannel = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCbComplaintChannel).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
