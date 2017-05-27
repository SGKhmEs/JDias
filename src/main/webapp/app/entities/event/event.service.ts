import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Event } from './event.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EventService {

    private resourceUrl = 'api/events';
    private resourceSearchUrl = 'api/_search/events';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(event: Event): Observable<Event> {
        const copy = this.convert(event);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(event: Event): Observable<Event> {
        const copy = this.convert(event);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Event> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convertItemFromServer(entity: any) {
        entity.start = this.dateUtils
            .convertDateTimeFromServer(entity.start);
        entity.end = this.dateUtils
            .convertDateTimeFromServer(entity.end);
    }

    private convert(event: Event): Event {
        const copy: Event = Object.assign({}, event);

        copy.start = this.dateUtils.toDate(event.start);

        copy.end = this.dateUtils.toDate(event.end);
        return copy;
    }
}
