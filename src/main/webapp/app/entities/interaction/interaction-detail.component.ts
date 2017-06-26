import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Interaction } from './interaction.model';
import { InteractionService } from './interaction.service';

@Component({
    selector: 'jhi-interaction-detail',
    templateUrl: './interaction-detail.component.html'
})
export class InteractionDetailComponent implements OnInit, OnDestroy {

    interaction: Interaction;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private interactionService: InteractionService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['interaction']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInInteractions();
    }

    load (id) {
        this.interactionService.find(id).subscribe(interaction => {
            this.interaction = interaction;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInteractions() {
        this.eventSubscriber = this.eventManager.subscribe('interactionListModification', response => this.load(this.interaction.id));
    }

}
