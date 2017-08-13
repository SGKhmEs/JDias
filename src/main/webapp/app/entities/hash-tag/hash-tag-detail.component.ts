import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { HashTag } from './hash-tag.model';
import { HashTagService } from './hash-tag.service';

@Component({
    selector: 'jhi-hash-tag-detail',
    templateUrl: './hash-tag-detail.component.html'
})
export class HashTagDetailComponent implements OnInit, OnDestroy {

    hashTag: HashTag;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hashTagService: HashTagService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHashTags();
    }

    load(id) {
        this.hashTagService.find(id).subscribe((hashTag) => {
            this.hashTag = hashTag;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHashTags() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hashTagListModification',
            (response) => this.load(this.hashTag.id)
        );
    }
}
