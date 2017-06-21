import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Retraction } from './retraction.model';
import { RetractionService } from './retraction.service';

@Component({
    selector: 'jhi-retraction-detail',
    templateUrl: './retraction-detail.component.html'
})
export class RetractionDetailComponent implements OnInit, OnDestroy {

    retraction: Retraction;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private retractionService: RetractionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRetractions();
    }

    load(id) {
        this.retractionService.find(id).subscribe((retraction) => {
            this.retraction = retraction;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRetractions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'retractionListModification',
            (response) => this.load(this.retraction.id)
        );
    }
}
