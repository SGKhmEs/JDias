import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { StatusMessage } from './status-message.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class StatusMessageService {

    private resourceUrl = 'api/status-messages';
    private resourceSearchUrl = 'api/_search/status-messages';

    constructor(private http: Http) { }

    create(statusMessage: StatusMessage): Observable<StatusMessage> {
        const copy = this.convert(statusMessage);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(statusMessage: StatusMessage): Observable<StatusMessage> {
        const copy = this.convert(statusMessage);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<StatusMessage> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(statusMessage: StatusMessage): StatusMessage {
        const copy: StatusMessage = Object.assign({}, statusMessage);
        return copy;
    }
}
