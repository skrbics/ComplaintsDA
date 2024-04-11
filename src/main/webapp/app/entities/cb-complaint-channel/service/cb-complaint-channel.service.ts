import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICbComplaintChannel, NewCbComplaintChannel } from '../cb-complaint-channel.model';

export type PartialUpdateCbComplaintChannel = Partial<ICbComplaintChannel> & Pick<ICbComplaintChannel, 'id'>;

export type EntityResponseType = HttpResponse<ICbComplaintChannel>;
export type EntityArrayResponseType = HttpResponse<ICbComplaintChannel[]>;

@Injectable({ providedIn: 'root' })
export class CbComplaintChannelService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cb-complaint-channels');

  create(cbComplaintChannel: NewCbComplaintChannel): Observable<EntityResponseType> {
    return this.http.post<ICbComplaintChannel>(this.resourceUrl, cbComplaintChannel, { observe: 'response' });
  }

  update(cbComplaintChannel: ICbComplaintChannel): Observable<EntityResponseType> {
    return this.http.put<ICbComplaintChannel>(
      `${this.resourceUrl}/${this.getCbComplaintChannelIdentifier(cbComplaintChannel)}`,
      cbComplaintChannel,
      { observe: 'response' },
    );
  }

  partialUpdate(cbComplaintChannel: PartialUpdateCbComplaintChannel): Observable<EntityResponseType> {
    return this.http.patch<ICbComplaintChannel>(
      `${this.resourceUrl}/${this.getCbComplaintChannelIdentifier(cbComplaintChannel)}`,
      cbComplaintChannel,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICbComplaintChannel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICbComplaintChannel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCbComplaintChannelIdentifier(cbComplaintChannel: Pick<ICbComplaintChannel, 'id'>): number {
    return cbComplaintChannel.id;
  }

  compareCbComplaintChannel(o1: Pick<ICbComplaintChannel, 'id'> | null, o2: Pick<ICbComplaintChannel, 'id'> | null): boolean {
    return o1 && o2 ? this.getCbComplaintChannelIdentifier(o1) === this.getCbComplaintChannelIdentifier(o2) : o1 === o2;
  }

  addCbComplaintChannelToCollectionIfMissing<Type extends Pick<ICbComplaintChannel, 'id'>>(
    cbComplaintChannelCollection: Type[],
    ...cbComplaintChannelsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cbComplaintChannels: Type[] = cbComplaintChannelsToCheck.filter(isPresent);
    if (cbComplaintChannels.length > 0) {
      const cbComplaintChannelCollectionIdentifiers = cbComplaintChannelCollection.map(cbComplaintChannelItem =>
        this.getCbComplaintChannelIdentifier(cbComplaintChannelItem),
      );
      const cbComplaintChannelsToAdd = cbComplaintChannels.filter(cbComplaintChannelItem => {
        const cbComplaintChannelIdentifier = this.getCbComplaintChannelIdentifier(cbComplaintChannelItem);
        if (cbComplaintChannelCollectionIdentifiers.includes(cbComplaintChannelIdentifier)) {
          return false;
        }
        cbComplaintChannelCollectionIdentifiers.push(cbComplaintChannelIdentifier);
        return true;
      });
      return [...cbComplaintChannelsToAdd, ...cbComplaintChannelCollection];
    }
    return cbComplaintChannelCollection;
  }
}
