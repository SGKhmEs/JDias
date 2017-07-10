import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Tagging } from './tagging.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TaggingService {

    private resourceUrl = 'api/taggings';
    private resourceSearchUrl = 'api/_search/taggings';

    constructor(private http: Http) { }

    create(tagging: Tagging): Observable<Tagging> {
        const copy = this.convert(tagging);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(tagging: Tagging): Observable<Tagging> {
        const copy = this.convert(tagging);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Tagging> {
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

    private convert(tagging: Tagging): Tagging {
        const copy: Tagging = Object.assign({}, tagging);
        return copy;
    }
}
