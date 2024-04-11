import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbComplaintPhase, NewCbComplaintPhase } from '../cb-complaint-phase.model';

export type PartialUpdateCbComplaintPhase = Partial<ICbComplaintPhase> & Pick<ICbComplaintPhase, 'id'>;

export type EntityResponseType = HttpResponse<ICbComplaintPhase>;
export type EntityArrayResponseType = HttpResponse<ICbComplaintPhase[]>;

@Injectable({ providedIn: 'root' })
export class CbComplaintPhaseService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cb-complaint-phases');

  create(cbComplaintPhase: NewCbComplaintPhase): Observable<EntityResponseType> {
    return this.http.post<ICbComplaintPhase>(this.resourceUrl, cbComplaintPhase, { observe: 'response' });
  }

  update(cbComplaintPhase: ICbComplaintPhase): Observable<EntityResponseType> {
    return this.http.put<ICbComplaintPhase>(
      `${this.resourceUrl}/${this.getCbComplaintPhaseIdentifier(cbComplaintPhase)}`,
      cbComplaintPhase,
      { observe: 'response' },
    );
  }

  partialUpdate(cbComplaintPhase: PartialUpdateCbComplaintPhase): Observable<EntityResponseType> {
    return this.http.patch<ICbComplaintPhase>(
      `${this.resourceUrl}/${this.getCbComplaintPhaseIdentifier(cbComplaintPhase)}`,
      cbComplaintPhase,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbComplaintPhase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbComplaintPhase[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbComplaintPhaseIdentifier(cbComplaintPhase: Pick<ICbComplaintPhase, 'id'>): number {
    return cbComplaintPhase.id;
  }

  compareCbComplaintPhase(o1: Pick<ICbComplaintPhase, 'id'> | null, o2: Pick<ICbComplaintPhase, 'id'> | null): boolean {
    return o1 && o2 ? this.getCbComplaintPhaseIdentifier(o1) === this.getCbComplaintPhaseIdentifier(o2) : o1 === o2;
  }

  addCbComplaintPhaseToCollectionIfMissing<Type extends Pick<ICbComplaintPhase, 'id'>>(
    cbComplaintPhaseCollection: Type[],
    ...cbComplaintPhasesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbComplaintPhases: Type[] = cbComplaintPhasesToCheck.filter(isPresent);
    if (cbComplaintPhases.length > 0) {
      const cbComplaintPhaseCollectionIdentifiers = cbComplaintPhaseCollection.map(cbComplaintPhaseItem =>
        this.getCbComplaintPhaseIdentifier(cbComplaintPhaseItem),
      );
      const cbComplaintPhasesToAdd = cbComplaintPhases.filter(cbComplaintPhaseItem => {
        const cbComplaintPhaseIdentifier = this.getCbComplaintPhaseIdentifier(cbComplaintPhaseItem);
        if (cbComplaintPhaseCollectionIdentifiers.includes(cbComplaintPhaseIdentifier)) {
          return false;
        }
        cbComplaintPhaseCollectionIdentifiers.push(cbComplaintPhaseIdentifier);
        return true;
      });
      return [...cbComplaintPhasesToAdd, ...cbComplaintPhaseCollection];
    }
    return cbComplaintPhaseCollection;
  }
}
