import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { AspectVisiblity } from './aspect-visiblity.model';
import { AspectVisiblityService } from './aspect-visiblity.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-aspect-visiblity',
    templateUrl: './aspect-visiblity.component.html'
})
export class AspectVisiblityComponent implements OnInit, OnDestroy {
aspectVisiblities: AspectVisiblity[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private aspectVisiblityService: AspectVisiblityService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.aspectVisiblityService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.aspectVisiblities = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.aspectVisiblityService.query().subscribe(
            (res: ResponseWrapper) => {
                this.aspectVisiblities = res.json;
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
        this.registerChangeInAspectVisiblities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AspectVisiblity) {
        return item.id;
    }
    registerChangeInAspectVisiblities() {
        this.eventSubscriber = this.eventManager.subscribe('aspectVisiblityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
