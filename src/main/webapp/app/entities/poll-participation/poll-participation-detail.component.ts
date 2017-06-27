import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PollParticipation } from './poll-participation.model';
import { PollParticipationService } from './poll-participation.service';

@Component({
    selector: 'jhi-poll-participation-detail',
    templateUrl: './poll-participation-detail.component.html'
})
export class PollParticipationDetailComponent implements OnInit, OnDestroy {

    pollParticipation: PollParticipation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private pollParticipationService: PollParticipationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPollParticipations();
    }

    load(id) {
        this.pollParticipationService.find(id).subscribe((pollParticipation) => {
            this.pollParticipation = pollParticipation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPollParticipations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pollParticipationListModification',
            (response) => this.load(this.pollParticipation.id)
        );
    }
}
