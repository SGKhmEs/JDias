import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Aspectvisibility } from './aspectvisibility.model';
import { AspectvisibilityService } from './aspectvisibility.service';

@Component({
    selector: 'jhi-aspectvisibility-detail',
    templateUrl: './aspectvisibility-detail.component.html'
})
export class AspectvisibilityDetailComponent implements OnInit, OnDestroy {

    aspectvisibility: Aspectvisibility;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private aspectvisibilityService: AspectvisibilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAspectvisibilities();
    }

    load(id) {
        this.aspectvisibilityService.find(id).subscribe((aspectvisibility) => {
            this.aspectvisibility = aspectvisibility;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAspectvisibilities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'aspectvisibilityListModification',
            (response) => this.load(this.aspectvisibility.id)
        );
    }
}
