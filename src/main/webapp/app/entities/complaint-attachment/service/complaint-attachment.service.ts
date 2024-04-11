import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IComplaintAttachment, NewComplaintAttachment } from '../complaint-attachment.model';

export type PartialUpdateComplaintAttachment = Partial<IComplaintAttachment> & Pick<IComplaintAttachment, 'id'>;

export type EntityResponseType = HttpResponse<IComplaintAttachment>;
export type EntityArrayResponseType = HttpResponse<IComplaintAttachment[]>;

@Injectable({ providedIn: 'root' })
export class ComplaintAttachmentService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/complaint-attachments');

  create(complaintAttachment: NewComplaintAttachment): Observable<EntityResponseType> {
    return this.http.post<IComplaintAttachment>(this.resourceUrl, complaintAttachment, { observe: 'response' });
  }

  update(complaintAttachment: IComplaintAttachment): Observable<EntityResponseType> {
    return this.http.put<IComplaintAttachment>(
      `${this.resourceUrl}/${this.getComplaintAttachmentIdentifier(complaintAttachment)}`,
      complaintAttachment,
      { observe: 'response' },
    );
  }

  partialUpdate(complaintAttachment: PartialUpdateComplaintAttachment): Observable<EntityResponseType> {
    return this.http.patch<IComplaintAttachment>(
      `${this.resourceUrl}/${this.getComplaintAttachmentIdentifier(complaintAttachment)}`,
      complaintAttachment,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComplaintAttachment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComplaintAttachment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getComplaintAttachmentIdentifier(complaintAttachment: Pick<IComplaintAttachment, 'id'>): number {
    return complaintAttachment.id;
  }

  compareComplaintAttachment(o1: Pick<IComplaintAttachment, 'id'> | null, o2: Pick<IComplaintAttachment, 'id'> | null): boolean {
    return o1 && o2 ? this.getComplaintAttachmentIdentifier(o1) === this.getComplaintAttachmentIdentifier(o2) : o1 === o2;
  }

  addComplaintAttachmentToCollectionIfMissing<Type extends Pick<IComplaintAttachment, 'id'>>(
    complaintAttachmentCollection: Type[],
    ...complaintAttachmentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const complaintAttachments: Type[] = complaintAttachmentsToCheck.filter(isPresent);
    if (complaintAttachments.length > 0) {
      const complaintAttachmentCollectionIdentifiers = complaintAttachmentCollection.map(complaintAttachmentItem =>
        this.getComplaintAttachmentIdentifier(complaintAttachmentItem),
      );
      const complaintAttachmentsToAdd = complaintAttachments.filter(complaintAttachmentItem => {
        const complaintAttachmentIdentifier = this.getComplaintAttachmentIdentifier(complaintAttachmentItem);
        if (complaintAttachmentCollectionIdentifiers.includes(complaintAttachmentIdentifier)) {
          return false;
        }
        complaintAttachmentCollectionIdentifiers.push(complaintAttachmentIdentifier);
        return true;
      });
      return [...complaintAttachmentsToAdd, ...complaintAttachmentCollection];
    }
    return complaintAttachmentCollection;
  }
}
