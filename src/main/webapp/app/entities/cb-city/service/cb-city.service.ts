import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbCity, NewCbCity } from '../cb-city.model';

export type PartialUpdateCbCity = Partial<ICbCity> & Pick<ICbCity, 'id'>;

export type EntityResponseType = HttpResponse<ICbCity>;
export type EntityArrayResponseType = HttpResponse<ICbCity[]>;

@Injectable({ providedIn: 'root' })
export class CbCityService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cb-cities');

  create(cbCity: NewCbCity): Observable<EntityResponseType> {
    return this.http.post<ICbCity>(this.resourceUrl, cbCity, { observe: 'response' });
  }

  update(cbCity: ICbCity): Observable<EntityResponseType> {
    return this.http.put<ICbCity>(`${this.resourceUrl}/${this.getCbCityIdentifier(cbCity)}`, cbCity, { observe: 'response' });
  }

  partialUpdate(cbCity: PartialUpdateCbCity): Observable<EntityResponseType> {
    return this.http.patch<ICbCity>(`${this.resourceUrl}/${this.getCbCityIdentifier(cbCity)}`, cbCity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbCity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbCity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbCityIdentifier(cbCity: Pick<ICbCity, 'id'>): number {
    return cbCity.id;
  }

  compareCbCity(o1: Pick<ICbCity, 'id'> | null, o2: Pick<ICbCity, 'id'> | null): boolean {
    return o1 && o2 ? this.getCbCityIdentifier(o1) === this.getCbCityIdentifier(o2) : o1 === o2;
  }

  addCbCityToCollectionIfMissing<Type extends Pick<ICbCity, 'id'>>(
    cbCityCollection: Type[],
    ...cbCitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbCities: Type[] = cbCitiesToCheck.filter(isPresent);
    if (cbCities.length > 0) {
      const cbCityCollectionIdentifiers = cbCityCollection.map(cbCityItem => this.getCbCityIdentifier(cbCityItem));
      const cbCitiesToAdd = cbCities.filter(cbCityItem => {
        const cbCityIdentifier = this.getCbCityIdentifier(cbCityItem);
        if (cbCityCollectionIdentifiers.includes(cbCityIdentifier)) {
          return false;
        }
        cbCityCollectionIdentifiers.push(cbCityIdentifier);
        return true;
      });
      return [...cbCitiesToAdd, ...cbCityCollection];
    }
    return cbCityCollection;
  }
}
