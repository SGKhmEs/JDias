import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Like } from './like.model';
import { LikeService } from './like.service';

@Component({
    selector: 'jhi-like-detail',
    templateUrl: './like-detail.component.html'
})
export class LikeDetailComponent implements OnInit, OnDestroy {

    like: Like;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private likeService: LikeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLikes();
    }

    load(id) {
        this.likeService.find(id).subscribe((like) => {
            this.like = like;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLikes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'likeListModification',
            (response) => this.load(this.like.id)
        );
    }
}
