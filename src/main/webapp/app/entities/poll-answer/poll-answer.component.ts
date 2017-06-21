import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PollAnswer } from './poll-answer.model';
import { PollAnswerService } from './poll-answer.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-poll-answer',
    templateUrl: './poll-answer.component.html'
})
export class PollAnswerComponent implements OnInit, OnDestroy {
pollAnswers: PollAnswer[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private pollAnswerService: PollAnswerService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.pollAnswerService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.pollAnswers = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.pollAnswerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.pollAnswers = res.json;
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
        this.registerChangeInPollAnswers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PollAnswer) {
        return item.id;
    }
    registerChangeInPollAnswers() {
        this.eventSubscriber = this.eventManager.subscribe('pollAnswerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
