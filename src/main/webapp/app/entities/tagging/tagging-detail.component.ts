import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Tagging } from './tagging.model';
import { TaggingService } from './tagging.service';

@Component({
    selector: 'jhi-tagging-detail',
    templateUrl: './tagging-detail.component.html'
})
export class TaggingDetailComponent implements OnInit, OnDestroy {

    tagging: Tagging;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private taggingService: TaggingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTaggings();
    }

    load(id) {
        this.taggingService.find(id).subscribe((tagging) => {
            this.tagging = tagging;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTaggings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'taggingListModification',
            (response) => this.load(this.tagging.id)
        );
    }
}
