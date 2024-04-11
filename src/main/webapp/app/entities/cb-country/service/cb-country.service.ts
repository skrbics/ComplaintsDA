import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbCountry, NewCbCountry } from '../cb-country.model';

export type PartialUpdateCbCountry = Partial<ICbCountry> & Pick<ICbCountry, 'id'>;

export type EntityResponseType = HttpResponse<ICbCountry>;
export type EntityArrayResponseType = HttpResponse<ICbCountry[]>;

@Injectable({ providedIn: 'root' })
export class CbCountryService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cb-countries');

  create(cbCountry: NewCbCountry): Observable<EntityResponseType> {
    return this.http.post<ICbCountry>(this.resourceUrl, cbCountry, { observe: 'response' });
  }

  update(cbCountry: ICbCountry): Observable<EntityResponseType> {
    return this.http.put<ICbCountry>(`${this.resourceUrl}/${this.getCbCountryIdentifier(cbCountry)}`, cbCountry, { observe: 'response' });
  }

  partialUpdate(cbCountry: PartialUpdateCbCountry): Observable<EntityResponseType> {
    return this.http.patch<ICbCountry>(`${this.resourceUrl}/${this.getCbCountryIdentifier(cbCountry)}`, cbCountry, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbCountry>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbCountry[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbCountryIdentifier(cbCountry: Pick<ICbCountry, 'id'>): number {
    return cbCountry.id;
  }

  compareCbCountry(o1: Pick<ICbCountry, 'id'> | null, o2: Pick<ICbCountry, 'id'> | null): boolean {
    return o1 && o2 ? this.getCbCountryIdentifier(o1) === this.getCbCountryIdentifier(o2) : o1 === o2;
  }

  addCbCountryToCollectionIfMissing<Type extends Pick<ICbCountry, 'id'>>(
    cbCountryCollection: Type[],
    ...cbCountriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbCountries: Type[] = cbCountriesToCheck.filter(isPresent);
    if (cbCountries.length > 0) {
      const cbCountryCollectionIdentifiers = cbCountryCollection.map(cbCountryItem => this.getCbCountryIdentifier(cbCountryItem));
      const cbCountriesToAdd = cbCountries.filter(cbCountryItem => {
        const cbCountryIdentifier = this.getCbCountryIdentifier(cbCountryItem);
        if (cbCountryCollectionIdentifiers.includes(cbCountryIdentifier)) {
          return false;
        }
        cbCountryCollectionIdentifiers.push(cbCountryIdentifier);
        return true;
      });
      return [...cbCountriesToAdd, ...cbCountryCollection];
    }
    return cbCountryCollection;
  }
}
