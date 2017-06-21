import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { AspectMembership } from './aspect-membership.model';
import { AspectMembershipService } from './aspect-membership.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-aspect-membership',
    templateUrl: './aspect-membership.component.html'
})
export class AspectMembershipComponent implements OnInit, OnDestroy {
aspectMemberships: AspectMembership[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private aspectMembershipService: AspectMembershipService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.aspectMembershipService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.aspectMemberships = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.aspectMembershipService.query().subscribe(
            (res: ResponseWrapper) => {
                this.aspectMemberships = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAspectMemberships();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AspectMembership) {
        return item.id;
    }
    registerChangeInAspectMemberships() {
        this.eventSubscriber = this.eventManager.subscribe('aspectMembershipListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
