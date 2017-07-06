import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { AspectVisibility } from './aspect-visibility.model';
import { AspectVisibilityService } from './aspect-visibility.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-aspect-visibility',
    templateUrl: './aspect-visibility.component.html'
})
export class AspectVisibilityComponent implements OnInit, OnDestroy {
aspectVisibilities: AspectVisibility[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private aspectVisibilityService: AspectVisibilityService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.aspectVisibilityService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.aspectVisibilities = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.aspectVisibilityService.query().subscribe(
            (res: ResponseWrapper) => {
                this.aspectVisibilities = res.json;
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
        this.registerChangeInAspectVisibilities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AspectVisibility) {
        return item.id;
    }
    registerChangeInAspectVisibilities() {
        this.eventSubscriber = this.eventManager.subscribe('aspectVisibilityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
