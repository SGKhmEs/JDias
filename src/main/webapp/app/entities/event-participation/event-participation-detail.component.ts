import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { EventParticipation } from './event-participation.model';
import { EventParticipationService } from './event-participation.service';

@Component({
    selector: 'jhi-event-participation-detail',
    templateUrl: './event-participation-detail.component.html'
})
export class EventParticipationDetailComponent implements OnInit, OnDestroy {

    eventParticipation: EventParticipation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private eventParticipationService: EventParticipationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEventParticipations();
    }

    load(id) {
        this.eventParticipationService.find(id).subscribe((eventParticipation) => {
            this.eventParticipation = eventParticipation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEventParticipations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'eventParticipationListModification',
            (response) => this.load(this.eventParticipation.id)
        );
    }
}
