import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbLocation, NewCbLocation } from '../cb-location.model';

export type PartialUpdateCbLocation = Partial<ICbLocation> & Pick<ICbLocation, 'id'>;

export type EntityResponseType = HttpResponse<ICbLocation>;
export type EntityArrayResponseType = HttpResponse<ICbLocation[]>;

@Injectable({ providedIn: 'root' })
export class CbLocationService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cb-locations');

  create(cbLocation: NewCbLocation): Observable<EntityResponseType> {
    return this.http.post<ICbLocation>(this.resourceUrl, cbLocation, { observe: 'response' });
  }

  update(cbLocation: ICbLocation): Observable<EntityResponseType> {
    return this.http.put<ICbLocation>(`${this.resourceUrl}/${this.getCbLocationIdentifier(cbLocation)}`, cbLocation, {
      observe: 'response',
    });
  }

  partialUpdate(cbLocation: PartialUpdateCbLocation): Observable<EntityResponseType> {
    return this.http.patch<ICbLocation>(`${this.resourceUrl}/${this.getCbLocationIdentifier(cbLocation)}`, cbLocation, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbLocationIdentifier(cbLocation: Pick<ICbLocation, 'id'>): number {
    return cbLocation.id;
  }

  compareCbLocation(o1: Pick<ICbLocation, 'id'> | null, o2: Pick<ICbLocation, 'id'> | null): boolean {
    return o1 && o2 ? this.getCbLocationIdentifier(o1) === this.getCbLocationIdentifier(o2) : o1 === o2;
  }

  addCbLocationToCollectionIfMissing<Type extends Pick<ICbLocation, 'id'>>(
    cbLocationCollection: Type[],
    ...cbLocationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbLocations: Type[] = cbLocationsToCheck.filter(isPresent);
    if (cbLocations.length > 0) {
      const cbLocationCollectionIdentifiers = cbLocationCollection.map(cbLocationItem => this.getCbLocationIdentifier(cbLocationItem));
      const cbLocationsToAdd = cbLocations.filter(cbLocationItem => {
        const cbLocationIdentifier = this.getCbLocationIdentifier(cbLocationItem);
        if (cbLocationCollectionIdentifiers.includes(cbLocationIdentifier)) {
          return false;
        }
        cbLocationCollectionIdentifiers.push(cbLocationIdentifier);
        return true;
      });
      return [...cbLocationsToAdd, ...cbLocationCollection];
    }
    return cbLocationCollection;
  }
}
