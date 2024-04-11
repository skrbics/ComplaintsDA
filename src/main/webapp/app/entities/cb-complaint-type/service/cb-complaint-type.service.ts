import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbComplaintType, NewCbComplaintType } from '../cb-complaint-type.model';

export type PartialUpdateCbComplaintType = Partial<ICbComplaintType> & Pick<ICbComplaintType, 'id'>;

export type EntityResponseType = HttpResponse<ICbComplaintType>;
export type EntityArrayResponseType = HttpResponse<ICbComplaintType[]>;

@Injectable({ providedIn: 'root' })
export class CbComplaintTypeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cb-complaint-types');

  create(cbComplaintType: NewCbComplaintType): Observable<EntityResponseType> {
    return this.http.post<ICbComplaintType>(this.resourceUrl, cbComplaintType, { observe: 'response' });
  }

  update(cbComplaintType: ICbComplaintType): Observable<EntityResponseType> {
    return this.http.put<ICbComplaintType>(`${this.resourceUrl}/${this.getCbComplaintTypeIdentifier(cbComplaintType)}`, cbComplaintType, {
      observe: 'response',
    });
  }

  partialUpdate(cbComplaintType: PartialUpdateCbComplaintType): Observable<EntityResponseType> {
    return this.http.patch<ICbComplaintType>(`${this.resourceUrl}/${this.getCbComplaintTypeIdentifier(cbComplaintType)}`, cbComplaintType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbComplaintType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbComplaintType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbComplaintTypeIdentifier(cbComplaintType: Pick<ICbComplaintType, 'id'>): number {
    return cbComplaintType.id;
  }

  compareCbComplaintType(o1: Pick<ICbComplaintType, 'id'> | null, o2: Pick<ICbComplaintType, 'id'> | null): boolean {
    return o1 && o2 ? this.getCbComplaintTypeIdentifier(o1) === this.getCbComplaintTypeIdentifier(o2) : o1 === o2;
  }

  addCbComplaintTypeToCollectionIfMissing<Type extends Pick<ICbComplaintType, 'id'>>(
    cbComplaintTypeCollection: Type[],
    ...cbComplaintTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbComplaintTypes: Type[] = cbComplaintTypesToCheck.filter(isPresent);
    if (cbComplaintTypes.length > 0) {
      const cbComplaintTypeCollectionIdentifiers = cbComplaintTypeCollection.map(cbComplaintTypeItem =>
        this.getCbComplaintTypeIdentifier(cbComplaintTypeItem),
      );
      const cbComplaintTypesToAdd = cbComplaintTypes.filter(cbComplaintTypeItem => {
        const cbComplaintTypeIdentifier = this.getCbComplaintTypeIdentifier(cbComplaintTypeItem);
        if (cbComplaintTypeCollectionIdentifiers.includes(cbComplaintTypeIdentifier)) {
          return false;
        }
        cbComplaintTypeCollectionIdentifiers.push(cbComplaintTypeIdentifier);
        return true;
      });
      return [...cbComplaintTypesToAdd, ...cbComplaintTypeCollection];
    }
    return cbComplaintTypeCollection;
  }
}
