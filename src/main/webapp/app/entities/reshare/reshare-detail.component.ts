import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Reshare } from './reshare.model';
import { ReshareService } from './reshare.service';

@Component({
    selector: 'jhi-reshare-detail',
    templateUrl: './reshare-detail.component.html'
})
export class ReshareDetailComponent implements OnInit, OnDestroy {

    reshare: Reshare;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private reshareService: ReshareService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReshares();
    }

    load(id) {
        this.reshareService.find(id).subscribe((reshare) => {
            this.reshare = reshare;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReshares() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reshareListModification',
            (response) => this.load(this.reshare.id)
        );
    }
}
