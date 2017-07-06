import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Aspectvisibility } from './aspectvisibility.model';
import { AspectvisibilityService } from './aspectvisibility.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-aspectvisibility',
    templateUrl: './aspectvisibility.component.html'
})
export class AspectvisibilityComponent implements OnInit, OnDestroy {
aspectvisibilities: Aspectvisibility[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private aspectvisibilityService: AspectvisibilityService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.aspectvisibilityService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.aspectvisibilities = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.aspectvisibilityService.query().subscribe(
            (res: ResponseWrapper) => {
                this.aspectvisibilities = res.json;
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
        this.registerChangeInAspectvisibilities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Aspectvisibility) {
        return item.id;
    }
    registerChangeInAspectvisibilities() {
        this.eventSubscriber = this.eventManager.subscribe('aspectvisibilityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
