import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AspectVisibility } from './aspect-visibility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AspectVisibilityService {

    private resourceUrl = 'api/aspect-visibilities';
    private resourceSearchUrl = 'api/_search/aspect-visibilities';

    constructor(private http: Http) { }

    create(aspectVisibility: AspectVisibility): Observable<AspectVisibility> {
        const copy = this.convert(aspectVisibility);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(aspectVisibility: AspectVisibility): Observable<AspectVisibility> {
        const copy = this.convert(aspectVisibility);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AspectVisibility> {
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

    private convert(aspectVisibility: AspectVisibility): AspectVisibility {
        const copy: AspectVisibility = Object.assign({}, aspectVisibility);
        return copy;
    }
}
