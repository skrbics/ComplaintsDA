import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbComplaintSubField, NewCbComplaintSubField } from '../cb-complaint-sub-field.model';

export type PartialUpdateCbComplaintSubField = Partial<ICbComplaintSubField> & Pick<ICbComplaintSubField, 'id'>;

export type EntityResponseType = HttpResponse<ICbComplaintSubField>;
export type EntityArrayResponseType = HttpResponse<ICbComplaintSubField[]>;

@Injectable({ providedIn: 'root' })
export class CbComplaintSubFieldService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cb-complaint-sub-fields');

  create(cbComplaintSubField: NewCbComplaintSubField): Observable<EntityResponseType> {
    return this.http.post<ICbComplaintSubField>(this.resourceUrl, cbComplaintSubField, { observe: 'response' });
  }

  update(cbComplaintSubField: ICbComplaintSubField): Observable<EntityResponseType> {
    return this.http.put<ICbComplaintSubField>(
      `${this.resourceUrl}/${this.getCbComplaintSubFieldIdentifier(cbComplaintSubField)}`,
      cbComplaintSubField,
      { observe: 'response' },
    );
  }

  partialUpdate(cbComplaintSubField: PartialUpdateCbComplaintSubField): Observable<EntityResponseType> {
    return this.http.patch<ICbComplaintSubField>(
      `${this.resourceUrl}/${this.getCbComplaintSubFieldIdentifier(cbComplaintSubField)}`,
      cbComplaintSubField,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbComplaintSubField>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbComplaintSubField[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbComplaintSubFieldIdentifier(cbComplaintSubField: Pick<ICbComplaintSubField, 'id'>): number {
    return cbComplaintSubField.id;
  }

  compareCbComplaintSubField(o1: Pick<ICbComplaintSubField, 'id'> | null, o2: Pick<ICbComplaintSubField, 'id'> | null): boolean {
    return o1 && o2 ? this.getCbComplaintSubFieldIdentifier(o1) === this.getCbComplaintSubFieldIdentifier(o2) : o1 === o2;
  }

  addCbComplaintSubFieldToCollectionIfMissing<Type extends Pick<ICbComplaintSubField, 'id'>>(
    cbComplaintSubFieldCollection: Type[],
    ...cbComplaintSubFieldsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbComplaintSubFields: Type[] = cbComplaintSubFieldsToCheck.filter(isPresent);
    if (cbComplaintSubFields.length > 0) {
      const cbComplaintSubFieldCollectionIdentifiers = cbComplaintSubFieldCollection.map(cbComplaintSubFieldItem =>
        this.getCbComplaintSubFieldIdentifier(cbComplaintSubFieldItem),
      );
      const cbComplaintSubFieldsToAdd = cbComplaintSubFields.filter(cbComplaintSubFieldItem => {
        const cbComplaintSubFieldIdentifier = this.getCbComplaintSubFieldIdentifier(cbComplaintSubFieldItem);
        if (cbComplaintSubFieldCollectionIdentifiers.includes(cbComplaintSubFieldIdentifier)) {
          return false;
        }
        cbComplaintSubFieldCollectionIdentifiers.push(cbComplaintSubFieldIdentifier);
        return true;
      });
      return [...cbComplaintSubFieldsToAdd, ...cbComplaintSubFieldCollection];
    }
    return cbComplaintSubFieldCollection;
  }
}
