import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { TagFollowing } from './tag-following.model';
import { TagFollowingService } from './tag-following.service';

@Component({
    selector: 'jhi-tag-following-detail',
    templateUrl: './tag-following-detail.component.html'
})
export class TagFollowingDetailComponent implements OnInit, OnDestroy {

    tagFollowing: TagFollowing;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private tagFollowingService: TagFollowingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTagFollowings();
    }

    load(id) {
        this.tagFollowingService.find(id).subscribe((tagFollowing) => {
            this.tagFollowing = tagFollowing;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTagFollowings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tagFollowingListModification',
            (response) => this.load(this.tagFollowing.id)
        );
    }
}
