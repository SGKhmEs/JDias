import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AspectVisiblity } from './aspect-visiblity.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AspectVisiblityService {

    private resourceUrl = 'api/aspect-visiblities';
    private resourceSearchUrl = 'api/_search/aspect-visiblities';

    constructor(private http: Http) { }

    create(aspectVisiblity: AspectVisiblity): Observable<AspectVisiblity> {
        const copy = this.convert(aspectVisiblity);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(aspectVisiblity: AspectVisiblity): Observable<AspectVisiblity> {
        const copy = this.convert(aspectVisiblity);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AspectVisiblity> {
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

    private convert(aspectVisiblity: AspectVisiblity): AspectVisiblity {
        const copy: AspectVisiblity = Object.assign({}, aspectVisiblity);
        return copy;
    }
}
