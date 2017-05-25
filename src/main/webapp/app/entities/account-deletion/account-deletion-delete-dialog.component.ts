import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AccountDeletion } from './account-deletion.model';
import { AccountDeletionPopupService } from './account-deletion-popup.service';
import { AccountDeletionService } from './account-deletion.service';

@Component({
    selector: 'jhi-account-deletion-delete-dialog',
    templateUrl: './account-deletion-delete-dialog.component.html'
})
export class AccountDeletionDeleteDialogComponent {

    accountDeletion: AccountDeletion;

    constructor(
        private accountDeletionService: AccountDeletionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.accountDeletionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'accountDeletionListModification',
                content: 'Deleted an accountDeletion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-account-deletion-delete-popup',
    template: ''
})
export class AccountDeletionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private accountDeletionPopupService: AccountDeletionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.accountDeletionPopupService
                .open(AccountDeletionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
