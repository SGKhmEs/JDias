import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserAccount } from './user-account.model';
import { UserAccountPopupService } from './user-account-popup.service';
import { UserAccountService } from './user-account.service';
import { User, UserService } from '../../shared';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-account-dialog',
    templateUrl: './user-account-dialog.component.html'
})
export class UserAccountDialogComponent implements OnInit {

    userAccount: UserAccount;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    people: Person[];
    rememberCreatedAtDp: any;
    currentSignInAtDp: any;
    lastSignInAtDp: any;
    createdAtDp: any;
    updatedAtDp: any;
    lockedAtDp: any;
    lastSeenDp: any;
    exportedAtDp: any;
    exportedPhotosAtDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private userAccountService: UserAccountService,
        private userService: UserService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService
            .query({filter: 'useraccount-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.userAccount.person || !this.userAccount.person.id) {
                    this.people = res.json;
                } else {
                    this.personService
                        .find(this.userAccount.person.id)
                        .subscribe((subRes: Person) => {
                            this.people = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userAccount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userAccountService.update(this.userAccount), false);
        } else {
            this.subscribeToSaveResponse(
                this.userAccountService.create(this.userAccount), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<UserAccount>, isCreated: boolean) {
        result.subscribe((res: UserAccount) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UserAccount, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.userAccount.created'
            : 'jDiasApp.userAccount.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'userAccountListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-account-popup',
    template: ''
})
export class UserAccountPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userAccountPopupService: UserAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.userAccountPopupService
                    .open(UserAccountDialogComponent, params['id']);
            } else {
                this.modalRef = this.userAccountPopupService
                    .open(UserAccountDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
