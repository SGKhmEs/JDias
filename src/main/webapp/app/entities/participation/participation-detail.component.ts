import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Participation } from './participation.model';
import { ParticipationService } from './participation.service';

@Component({
    selector: 'jhi-participation-detail',
    templateUrl: './participation-detail.component.html'
})
export class ParticipationDetailComponent implements OnInit, OnDestroy {

    participation: Participation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private participationService: ParticipationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParticipations();
    }

    load(id) {
        this.participationService.find(id).subscribe((participation) => {
            this.participation = participation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParticipations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'participationListModification',
            (response) => this.load(this.participation.id)
        );
    }
}
