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
    authorities: any[];
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
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.accountDeletion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.accountDeletionService.update(this.accountDeletion), false);
        } else {
            this.subscribeToSaveResponse(
                this.accountDeletionService.create(this.accountDeletion), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<AccountDeletion>, isCreated: boolean) {
        result.subscribe((res: AccountDeletion) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AccountDeletion, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.accountDeletion.created'
            : 'jDiasApp.accountDeletion.updated',
            { param : result.id }, null);

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

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private accountDeletionPopupService: AccountDeletionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.accountDeletionPopupService
                    .open(AccountDeletionDialogComponent, params['id']);
            } else {
                this.modalRef = this.accountDeletionPopupService
                    .open(AccountDeletionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
