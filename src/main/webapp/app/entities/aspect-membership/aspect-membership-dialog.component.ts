import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AspectMembership } from './aspect-membership.model';
import { AspectMembershipPopupService } from './aspect-membership-popup.service';
import { AspectMembershipService } from './aspect-membership.service';
import { Aspect, AspectService } from '../aspect';
import { Contact, ContactService } from '../contact';
import { UserAccount, UserAccountService } from '../user-account';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-aspect-membership-dialog',
    templateUrl: './aspect-membership-dialog.component.html'
})
export class AspectMembershipDialogComponent implements OnInit {

    aspectMembership: AspectMembership;
    authorities: any[];
    isSaving: boolean;

    aspects: Aspect[];

    contacts: Contact[];

    useraccounts: UserAccount[];
    createdAtDp: any;
    updatedAtDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private aspectMembershipService: AspectMembershipService,
        private aspectService: AspectService,
        private contactService: ContactService,
        private userAccountService: UserAccountService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.aspectService.query()
            .subscribe((res: ResponseWrapper) => { this.aspects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.contactService.query()
            .subscribe((res: ResponseWrapper) => { this.contacts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.useraccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.aspectMembership.id !== undefined) {
            this.subscribeToSaveResponse(
                this.aspectMembershipService.update(this.aspectMembership), false);
        } else {
            this.subscribeToSaveResponse(
                this.aspectMembershipService.create(this.aspectMembership), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<AspectMembership>, isCreated: boolean) {
        result.subscribe((res: AspectMembership) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AspectMembership, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.aspectMembership.created'
            : 'jDiasApp.aspectMembership.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'aspectMembershipListModification', content: 'OK'});
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

    trackAspectById(index: number, item: Aspect) {
        return item.id;
    }

    trackContactById(index: number, item: Contact) {
        return item.id;
    }

    trackUserAccountById(index: number, item: UserAccount) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-aspect-membership-popup',
    template: ''
})
export class AspectMembershipPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectMembershipPopupService: AspectMembershipPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.aspectMembershipPopupService
                    .open(AspectMembershipDialogComponent, params['id']);
            } else {
                this.modalRef = this.aspectMembershipPopupService
                    .open(AspectMembershipDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
