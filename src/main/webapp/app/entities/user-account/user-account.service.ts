import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { UserAccount } from './user-account.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserAccountService {

    private resourceUrl = 'api/user-accounts';
    private resourceSearchUrl = 'api/_search/user-accounts';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(userAccount: UserAccount): Observable<UserAccount> {
        const copy = this.convert(userAccount);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(userAccount: UserAccount): Observable<UserAccount> {
        const copy = this.convert(userAccount);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<UserAccount> {
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
        entity.rememberCreatedAt = this.dateUtils
            .convertDateTimeFromServer(entity.rememberCreatedAt);
        entity.currentSignInAt = this.dateUtils
            .convertDateTimeFromServer(entity.currentSignInAt);
        entity.lastSignInAt = this.dateUtils
            .convertDateTimeFromServer(entity.lastSignInAt);
        entity.createdAt = this.dateUtils
            .convertDateTimeFromServer(entity.createdAt);
        entity.updatedAt = this.dateUtils
            .convertDateTimeFromServer(entity.updatedAt);
        entity.lockedAt = this.dateUtils
            .convertDateTimeFromServer(entity.lockedAt);
        entity.lastSeen = this.dateUtils
            .convertDateTimeFromServer(entity.lastSeen);
        entity.exportedAt = this.dateUtils
            .convertDateTimeFromServer(entity.exportedAt);
        entity.exportedPhotosAt = this.dateUtils
            .convertDateTimeFromServer(entity.exportedPhotosAt);
    }

    private convert(userAccount: UserAccount): UserAccount {
        const copy: UserAccount = Object.assign({}, userAccount);

        copy.rememberCreatedAt = this.dateUtils.toDate(userAccount.rememberCreatedAt);

        copy.currentSignInAt = this.dateUtils.toDate(userAccount.currentSignInAt);

        copy.lastSignInAt = this.dateUtils.toDate(userAccount.lastSignInAt);

        copy.createdAt = this.dateUtils.toDate(userAccount.createdAt);

        copy.updatedAt = this.dateUtils.toDate(userAccount.updatedAt);

        copy.lockedAt = this.dateUtils.toDate(userAccount.lockedAt);

        copy.lastSeen = this.dateUtils.toDate(userAccount.lastSeen);

        copy.exportedAt = this.dateUtils.toDate(userAccount.exportedAt);

        copy.exportedPhotosAt = this.dateUtils.toDate(userAccount.exportedPhotosAt);
        return copy;
    }
}
