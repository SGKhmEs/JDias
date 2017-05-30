import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PollParticipation } from './poll-participation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PollParticipationService {

    private resourceUrl = 'api/poll-participations';
    private resourceSearchUrl = 'api/_search/poll-participations';

    constructor(private http: Http) { }

    create(pollParticipation: PollParticipation): Observable<PollParticipation> {
        const copy = this.convert(pollParticipation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(pollParticipation: PollParticipation): Observable<PollParticipation> {
        const copy = this.convert(pollParticipation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PollParticipation> {
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(pollParticipation: PollParticipation): PollParticipation {
        const copy: PollParticipation = Object.assign({}, pollParticipation);
        return copy;
    }
}
