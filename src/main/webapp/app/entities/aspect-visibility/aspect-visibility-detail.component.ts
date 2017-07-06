import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { AspectVisibility } from './aspect-visibility.model';
import { AspectVisibilityService } from './aspect-visibility.service';

@Component({
    selector: 'jhi-aspect-visibility-detail',
    templateUrl: './aspect-visibility-detail.component.html'
})
export class AspectVisibilityDetailComponent implements OnInit, OnDestroy {

    aspectVisibility: AspectVisibility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private aspectVisibilityService: AspectVisibilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAspectVisibilities();
    }

    load(id) {
        this.aspectVisibilityService.find(id).subscribe((aspectVisibility) => {
            this.aspectVisibility = aspectVisibility;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAspectVisibilities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'aspectVisibilityListModification',
            (response) => this.load(this.aspectVisibility.id)
        );
    }
}
