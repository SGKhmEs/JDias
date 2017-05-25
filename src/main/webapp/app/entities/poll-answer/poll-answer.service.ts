import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PollAnswer } from './poll-answer.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PollAnswerService {

    private resourceUrl = 'api/poll-answers';
    private resourceSearchUrl = 'api/_search/poll-answers';

    constructor(private http: Http) { }

    create(pollAnswer: PollAnswer): Observable<PollAnswer> {
        const copy = this.convert(pollAnswer);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(pollAnswer: PollAnswer): Observable<PollAnswer> {
        const copy = this.convert(pollAnswer);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PollAnswer> {
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

    private convert(pollAnswer: PollAnswer): PollAnswer {
        const copy: PollAnswer = Object.assign({}, pollAnswer);
        return copy;
    }
}
