import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Retraction } from './retraction.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RetractionService {

    private resourceUrl = 'api/retractions';
    private resourceSearchUrl = 'api/_search/retractions';

    constructor(private http: Http) { }

    create(retraction: Retraction): Observable<Retraction> {
        const copy = this.convert(retraction);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(retraction: Retraction): Observable<Retraction> {
        const copy = this.convert(retraction);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Retraction> {
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

    private convert(retraction: Retraction): Retraction {
        const copy: Retraction = Object.assign({}, retraction);
        return copy;
    }
}
