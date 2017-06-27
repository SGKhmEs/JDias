import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { UserAccount } from './user-account.model';
import { UserAccountService } from './user-account.service';

@Component({
    selector: 'jhi-user-account-detail',
    templateUrl: './user-account-detail.component.html'
})
export class UserAccountDetailComponent implements OnInit, OnDestroy {

    userAccount: UserAccount;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private userAccountService: UserAccountService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserAccounts();
    }

    load(id) {
        this.userAccountService.find(id).subscribe((userAccount) => {
            this.userAccount = userAccount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserAccounts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userAccountListModification',
            (response) => this.load(this.userAccount.id)
        );
    }
}
