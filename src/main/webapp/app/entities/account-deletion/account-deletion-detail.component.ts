import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { AccountDeletion } from './account-deletion.model';
import { AccountDeletionService } from './account-deletion.service';

@Component({
    selector: 'jhi-account-deletion-detail',
    templateUrl: './account-deletion-detail.component.html'
})
export class AccountDeletionDetailComponent implements OnInit, OnDestroy {

    accountDeletion: AccountDeletion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private accountDeletionService: AccountDeletionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAccountDeletions();
    }

    load(id) {
        this.accountDeletionService.find(id).subscribe((accountDeletion) => {
            this.accountDeletion = accountDeletion;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAccountDeletions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'accountDeletionListModification',
            (response) => this.load(this.accountDeletion.id)
        );
    }
}
