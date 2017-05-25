import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AccountDeletion } from './account-deletion.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AccountDeletionService {

    private resourceUrl = 'api/account-deletions';
    private resourceSearchUrl = 'api/_search/account-deletions';

    constructor(private http: Http) { }

    create(accountDeletion: AccountDeletion): Observable<AccountDeletion> {
        const copy = this.convert(accountDeletion);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(accountDeletion: AccountDeletion): Observable<AccountDeletion> {
        const copy = this.convert(accountDeletion);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AccountDeletion> {
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

    private convert(accountDeletion: AccountDeletion): AccountDeletion {
        const copy: AccountDeletion = Object.assign({}, accountDeletion);
        return copy;
    }
}
