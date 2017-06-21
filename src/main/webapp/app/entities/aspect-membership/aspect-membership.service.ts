import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { AspectMembership } from './aspect-membership.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AspectMembershipService {

    private resourceUrl = 'api/aspect-memberships';
    private resourceSearchUrl = 'api/_search/aspect-memberships';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(aspectMembership: AspectMembership): Observable<AspectMembership> {
        const copy = this.convert(aspectMembership);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(aspectMembership: AspectMembership): Observable<AspectMembership> {
        const copy = this.convert(aspectMembership);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<AspectMembership> {
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

    private convert(aspectMembership: AspectMembership): AspectMembership {
        const copy: AspectMembership = Object.assign({}, aspectMembership);
        copy.createdAt = this.dateUtils
            .convertLocalDateToServer(aspectMembership.createdAt);
        copy.updatedAt = this.dateUtils
            .convertLocalDateToServer(aspectMembership.updatedAt);
        return copy;
    }
}
