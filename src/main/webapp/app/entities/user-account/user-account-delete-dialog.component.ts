import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { UserAccount } from './user-account.model';
import { UserAccountPopupService } from './user-account-popup.service';
import { UserAccountService } from './user-account.service';

@Component({
    selector: 'jhi-user-account-delete-dialog',
    templateUrl: './user-account-delete-dialog.component.html'
})
export class UserAccountDeleteDialogComponent {

    userAccount: UserAccount;

    constructor(
        private userAccountService: UserAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userAccountListModification',
                content: 'Deleted an userAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-account-delete-popup',
    template: ''
})
export class UserAccountDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userAccountPopupService: UserAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.userAccountPopupService
                .open(UserAccountDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
