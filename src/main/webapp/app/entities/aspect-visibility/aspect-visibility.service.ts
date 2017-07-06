import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { AspectVisibility } from './aspect-visibility.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AspectVisibilityService {

    private resourceUrl = 'api/aspect-visibilities';
    private resourceSearchUrl = 'api/_search/aspect-visibilities';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(aspectVisibility: AspectVisibility): Observable<AspectVisibility> {
        const copy = this.convert(aspectVisibility);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(aspectVisibility: AspectVisibility): Observable<AspectVisibility> {
        const copy = this.convert(aspectVisibility);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<AspectVisibility> {
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.createdAt = this.dateUtils
            .convertLocalDateFromServer(entity.createdAt);
        entity.updatedAt = this.dateUtils
            .convertLocalDateFromServer(entity.updatedAt);
    }

    private convert(aspectVisibility: AspectVisibility): AspectVisibility {
        const copy: AspectVisibility = Object.assign({}, aspectVisibility);
        copy.createdAt = this.dateUtils
            .convertLocalDateToServer(aspectVisibility.createdAt);
        copy.updatedAt = this.dateUtils
            .convertLocalDateToServer(aspectVisibility.updatedAt);
        return copy;
    }
}
