import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { AspectMembership } from './aspect-membership.model';
import { AspectMembershipService } from './aspect-membership.service';

@Component({
    selector: 'jhi-aspect-membership-detail',
    templateUrl: './aspect-membership-detail.component.html'
})
export class AspectMembershipDetailComponent implements OnInit, OnDestroy {

    aspectMembership: AspectMembership;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private aspectMembershipService: AspectMembershipService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAspectMemberships();
    }

    load(id) {
        this.aspectMembershipService.find(id).subscribe((aspectMembership) => {
            this.aspectMembership = aspectMembership;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAspectMemberships() {
        this.eventSubscriber = this.eventManager.subscribe(
            'aspectMembershipListModification',
            (response) => this.load(this.aspectMembership.id)
        );
    }
}
