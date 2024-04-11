import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbApplicantCapacityType, NewCbApplicantCapacityType } from '../cb-applicant-capacity-type.model';

export type PartialUpdateCbApplicantCapacityType = Partial<ICbApplicantCapacityType> & Pick<ICbApplicantCapacityType, 'id'>;

export type EntityResponseType = HttpResponse<ICbApplicantCapacityType>;
export type EntityArrayResponseType = HttpResponse<ICbApplicantCapacityType[]>;

@Injectable({ providedIn: 'root' })
export class CbApplicantCapacityTypeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cb-applicant-capacity-types');

  create(cbApplicantCapacityType: NewCbApplicantCapacityType): Observable<EntityResponseType> {
    return this.http.post<ICbApplicantCapacityType>(this.resourceUrl, cbApplicantCapacityType, { observe: 'response' });
  }

  update(cbApplicantCapacityType: ICbApplicantCapacityType): Observable<EntityResponseType> {
    return this.http.put<ICbApplicantCapacityType>(
      `${this.resourceUrl}/${this.getCbApplicantCapacityTypeIdentifier(cbApplicantCapacityType)}`,
      cbApplicantCapacityType,
      { observe: 'response' },
    );
  }

  partialUpdate(cbApplicantCapacityType: PartialUpdateCbApplicantCapacityType): Observable<EntityResponseType> {
    return this.http.patch<ICbApplicantCapacityType>(
      `${this.resourceUrl}/${this.getCbApplicantCapacityTypeIdentifier(cbApplicantCapacityType)}`,
      cbApplicantCapacityType,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbApplicantCapacityType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbApplicantCapacityType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbApplicantCapacityTypeIdentifier(cbApplicantCapacityType: Pick<ICbApplicantCapacityType, 'id'>): number {
    return cbApplicantCapacityType.id;
  }

  compareCbApplicantCapacityType(
    o1: Pick<ICbApplicantCapacityType, 'id'> | null,
    o2: Pick<ICbApplicantCapacityType, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getCbApplicantCapacityTypeIdentifier(o1) === this.getCbApplicantCapacityTypeIdentifier(o2) : o1 === o2;
  }

  addCbApplicantCapacityTypeToCollectionIfMissing<Type extends Pick<ICbApplicantCapacityType, 'id'>>(
    cbApplicantCapacityTypeCollection: Type[],
    ...cbApplicantCapacityTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbApplicantCapacityTypes: Type[] = cbApplicantCapacityTypesToCheck.filter(isPresent);
    if (cbApplicantCapacityTypes.length > 0) {
      const cbApplicantCapacityTypeCollectionIdentifiers = cbApplicantCapacityTypeCollection.map(cbApplicantCapacityTypeItem =>
        this.getCbApplicantCapacityTypeIdentifier(cbApplicantCapacityTypeItem),
      );
      const cbApplicantCapacityTypesToAdd = cbApplicantCapacityTypes.filter(cbApplicantCapacityTypeItem => {
        const cbApplicantCapacityTypeIdentifier = this.getCbApplicantCapacityTypeIdentifier(cbApplicantCapacityTypeItem);
        if (cbApplicantCapacityTypeCollectionIdentifiers.includes(cbApplicantCapacityTypeIdentifier)) {
          return false;
        }
        cbApplicantCapacityTypeCollectionIdentifiers.push(cbApplicantCapacityTypeIdentifier);
        return true;
      });
      return [...cbApplicantCapacityTypesToAdd, ...cbApplicantCapacityTypeCollection];
    }
    return cbApplicantCapacityTypeCollection;
  }
}
