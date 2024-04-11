import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbComplaintField, NewCbComplaintField } from '../cb-complaint-field.model';

export type PartialUpdateCbComplaintField = Partial<ICbComplaintField> & Pick<ICbComplaintField, 'id'>;

export type EntityResponseType = HttpResponse<ICbComplaintField>;
export type EntityArrayResponseType = HttpResponse<ICbComplaintField[]>;

@Injectable({ providedIn: 'root' })
export class CbComplaintFieldService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cb-complaint-fields');

  create(cbComplaintField: NewCbComplaintField): Observable<EntityResponseType> {
    return this.http.post<ICbComplaintField>(this.resourceUrl, cbComplaintField, { observe: 'response' });
  }

  update(cbComplaintField: ICbComplaintField): Observable<EntityResponseType> {
    return this.http.put<ICbComplaintField>(
      `${this.resourceUrl}/${this.getCbComplaintFieldIdentifier(cbComplaintField)}`,
      cbComplaintField,
      { observe: 'response' },
    );
  }

  partialUpdate(cbComplaintField: PartialUpdateCbComplaintField): Observable<EntityResponseType> {
    return this.http.patch<ICbComplaintField>(
      `${this.resourceUrl}/${this.getCbComplaintFieldIdentifier(cbComplaintField)}`,
      cbComplaintField,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbComplaintField>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbComplaintField[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbComplaintFieldIdentifier(cbComplaintField: Pick<ICbComplaintField, 'id'>): number {
    return cbComplaintField.id;
  }

  compareCbComplaintField(o1: Pick<ICbComplaintField, 'id'> | null, o2: Pick<ICbComplaintField, 'id'> | null): boolean {
    return o1 && o2 ? this.getCbComplaintFieldIdentifier(o1) === this.getCbComplaintFieldIdentifier(o2) : o1 === o2;
  }

  addCbComplaintFieldToCollectionIfMissing<Type extends Pick<ICbComplaintField, 'id'>>(
    cbComplaintFieldCollection: Type[],
    ...cbComplaintFieldsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbComplaintFields: Type[] = cbComplaintFieldsToCheck.filter(isPresent);
    if (cbComplaintFields.length > 0) {
      const cbComplaintFieldCollectionIdentifiers = cbComplaintFieldCollection.map(cbComplaintFieldItem =>
        this.getCbComplaintFieldIdentifier(cbComplaintFieldItem),
      );
      const cbComplaintFieldsToAdd = cbComplaintFields.filter(cbComplaintFieldItem => {
        const cbComplaintFieldIdentifier = this.getCbComplaintFieldIdentifier(cbComplaintFieldItem);
        if (cbComplaintFieldCollectionIdentifiers.includes(cbComplaintFieldIdentifier)) {
          return false;
        }
        cbComplaintFieldCollectionIdentifiers.push(cbComplaintFieldIdentifier);
        return true;
      });
      return [...cbComplaintFieldsToAdd, ...cbComplaintFieldCollection];
    }
    return cbComplaintFieldCollection;
  }
}
