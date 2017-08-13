import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AccountDeletion } from './account-deletion.model';
import { AccountDeletionPopupService } from './account-deletion-popup.service';
import { AccountDeletionService } from './account-deletion.service';

@Component({
    selector: 'jhi-account-deletion-dialog',
    templateUrl: './account-deletion-dialog.component.html'
})
export class AccountDeletionDialogComponent implements OnInit {

    accountDeletion: AccountDeletion;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private accountDeletionService: AccountDeletionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.accountDeletion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.accountDeletionService.update(this.accountDeletion));
        } else {
            this.subscribeToSaveResponse(
                this.accountDeletionService.create(this.accountDeletion));
        }
    }

    private subscribeToSaveResponse(result: Observable<AccountDeletion>) {
        result.subscribe((res: AccountDeletion) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AccountDeletion) {
        this.eventManager.broadcast({ name: 'accountDeletionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-account-deletion-popup',
    template: ''
})
export class AccountDeletionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private accountDeletionPopupService: AccountDeletionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.accountDeletionPopupService
                    .open(AccountDeletionDialogComponent as Component, params['id']);
            } else {
                this.accountDeletionPopupService
                    .open(AccountDeletionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
