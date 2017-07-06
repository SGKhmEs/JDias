import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Aspect } from './aspect.model';
import { AspectService } from './aspect.service';

@Component({
    selector: 'jhi-aspect-detail',
    templateUrl: './aspect-detail.component.html'
})
export class AspectDetailComponent implements OnInit, OnDestroy {

    aspect: Aspect;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private aspectService: AspectService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAspects();
    }

    load(id) {
        this.aspectService.find(id).subscribe((aspect) => {
            this.aspect = aspect;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAspects() {
        this.eventSubscriber = this.eventManager.subscribe(
            'aspectListModification',
            (response) => this.load(this.aspect.id)
        );
    }
}
