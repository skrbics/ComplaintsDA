import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbAttachmentType, NewCbAttachmentType } from '../cb-attachment-type.model';

export type PartialUpdateCbAttachmentType = Partial<ICbAttachmentType> & Pick<ICbAttachmentType, 'id'>;

export type EntityResponseType = HttpResponse<ICbAttachmentType>;
export type EntityArrayResponseType = HttpResponse<ICbAttachmentType[]>;

@Injectable({ providedIn: 'root' })
export class CbAttachmentTypeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cb-attachment-types');

  create(cbAttachmentType: NewCbAttachmentType): Observable<EntityResponseType> {
    return this.http.post<ICbAttachmentType>(this.resourceUrl, cbAttachmentType, { observe: 'response' });
  }

  update(cbAttachmentType: ICbAttachmentType): Observable<EntityResponseType> {
    return this.http.put<ICbAttachmentType>(
      `${this.resourceUrl}/${this.getCbAttachmentTypeIdentifier(cbAttachmentType)}`,
      cbAttachmentType,
      { observe: 'response' },
    );
  }

  partialUpdate(cbAttachmentType: PartialUpdateCbAttachmentType): Observable<EntityResponseType> {
    return this.http.patch<ICbAttachmentType>(
      `${this.resourceUrl}/${this.getCbAttachmentTypeIdentifier(cbAttachmentType)}`,
      cbAttachmentType,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbAttachmentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbAttachmentType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbAttachmentTypeIdentifier(cbAttachmentType: Pick<ICbAttachmentType, 'id'>): number {
    return cbAttachmentType.id;
  }

  compareCbAttachmentType(o1: Pick<ICbAttachmentType, 'id'> | null, o2: Pick<ICbAttachmentType, 'id'> | null): boolean {
    return o1 && o2 ? this.getCbAttachmentTypeIdentifier(o1) === this.getCbAttachmentTypeIdentifier(o2) : o1 === o2;
  }

  addCbAttachmentTypeToCollectionIfMissing<Type extends Pick<ICbAttachmentType, 'id'>>(
    cbAttachmentTypeCollection: Type[],
    ...cbAttachmentTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbAttachmentTypes: Type[] = cbAttachmentTypesToCheck.filter(isPresent);
    if (cbAttachmentTypes.length > 0) {
      const cbAttachmentTypeCollectionIdentifiers = cbAttachmentTypeCollection.map(cbAttachmentTypeItem =>
        this.getCbAttachmentTypeIdentifier(cbAttachmentTypeItem),
      );
      const cbAttachmentTypesToAdd = cbAttachmentTypes.filter(cbAttachmentTypeItem => {
        const cbAttachmentTypeIdentifier = this.getCbAttachmentTypeIdentifier(cbAttachmentTypeItem);
        if (cbAttachmentTypeCollectionIdentifiers.includes(cbAttachmentTypeIdentifier)) {
          return false;
        }
        cbAttachmentTypeCollectionIdentifiers.push(cbAttachmentTypeIdentifier);
        return true;
      });
      return [...cbAttachmentTypesToAdd, ...cbAttachmentTypeCollection];
    }
    return cbAttachmentTypeCollection;
  }
}
