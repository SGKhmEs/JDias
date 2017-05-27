import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { AspectVisiblity } from './aspect-visiblity.model';
import { AspectVisiblityService } from './aspect-visiblity.service';

@Component({
    selector: 'jhi-aspect-visiblity-detail',
    templateUrl: './aspect-visiblity-detail.component.html'
})
export class AspectVisiblityDetailComponent implements OnInit, OnDestroy {

    aspectVisiblity: AspectVisiblity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private aspectVisiblityService: AspectVisiblityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAspectVisiblities();
    }

    load(id) {
        this.aspectVisiblityService.find(id).subscribe((aspectVisiblity) => {
            this.aspectVisiblity = aspectVisiblity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAspectVisiblities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'aspectVisiblityListModification',
            (response) => this.load(this.aspectVisiblity.id)
        );
    }
}
