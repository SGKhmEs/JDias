import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { HashTag } from './hash-tag.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class HashTagService {

    private resourceUrl = 'api/hash-tags';
    private resourceSearchUrl = 'api/_search/hash-tags';

    constructor(private http: Http) { }

    create(hashTag: HashTag): Observable<HashTag> {
        const copy = this.convert(hashTag);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(hashTag: HashTag): Observable<HashTag> {
        const copy = this.convert(hashTag);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<HashTag> {
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

    private convert(hashTag: HashTag): HashTag {
        const copy: HashTag = Object.assign({}, hashTag);
        return copy;
    }
}
