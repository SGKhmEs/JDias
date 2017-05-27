import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { TagFollowing } from './tag-following.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TagFollowingService {

    private resourceUrl = 'api/tag-followings';
    private resourceSearchUrl = 'api/_search/tag-followings';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(tagFollowing: TagFollowing): Observable<TagFollowing> {
        const copy = this.convert(tagFollowing);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(tagFollowing: TagFollowing): Observable<TagFollowing> {
        const copy = this.convert(tagFollowing);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<TagFollowing> {
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
        entity.createdAt = this.dateUtils
            .convertDateTimeFromServer(entity.createdAt);
        entity.updatedAt = this.dateUtils
            .convertDateTimeFromServer(entity.updatedAt);
    }

    private convert(tagFollowing: TagFollowing): TagFollowing {
        const copy: TagFollowing = Object.assign({}, tagFollowing);

        copy.createdAt = this.dateUtils.toDate(tagFollowing.createdAt);

        copy.updatedAt = this.dateUtils.toDate(tagFollowing.updatedAt);
        return copy;
    }
}
