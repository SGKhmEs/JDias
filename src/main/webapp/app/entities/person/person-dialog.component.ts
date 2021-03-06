import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Person } from './person.model';
import { PersonPopupService } from './person-popup.service';
import { PersonService } from './person.service';
import { Reshare, ReshareService } from '../reshare';
import { Profile, ProfileService } from '../profile';
import { AccountDeletion, AccountDeletionService } from '../account-deletion';
import { Conversation, ConversationService } from '../conversation';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-dialog',
    templateUrl: './person-dialog.component.html'
})
export class PersonDialogComponent implements OnInit {

    person: Person;
    authorities: any[];
    isSaving: boolean;

    reshares: Reshare[];

    profiles: Profile[];

    accountdeletions: AccountDeletion[];

    conversations: Conversation[];
    createdAtDp: any;
    updatedAtDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personService: PersonService,
        private reshareService: ReshareService,
        private profileService: ProfileService,
        private accountDeletionService: AccountDeletionService,
        private conversationService: ConversationService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.reshareService.query()
            .subscribe((res: ResponseWrapper) => { this.reshares = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.profileService
            .query({filter: 'person-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.person.profile || !this.person.profile.id) {
                    this.profiles = res.json;
                } else {
                    this.profileService
                        .find(this.person.profile.id)
                        .subscribe((subRes: Profile) => {
                            this.profiles = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.accountDeletionService
            .query({filter: 'person-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.person.accountdeletion || !this.person.accountdeletion.id) {
                    this.accountdeletions = res.json;
                } else {
                    this.accountDeletionService
                        .find(this.person.accountdeletion.id)
                        .subscribe((subRes: AccountDeletion) => {
                            this.accountdeletions = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.conversationService.query()
            .subscribe((res: ResponseWrapper) => { this.conversations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.person.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personService.update(this.person), false);
        } else {
            this.subscribeToSaveResponse(
                this.personService.create(this.person), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Person>, isCreated: boolean) {
        result.subscribe((res: Person) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Person, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.person.created'
            : 'jDiasApp.person.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'personListModification', content: 'OK'});
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

    trackReshareById(index: number, item: Reshare) {
        return item.id;
    }

    trackProfileById(index: number, item: Profile) {
        return item.id;
    }

    trackAccountDeletionById(index: number, item: AccountDeletion) {
        return item.id;
    }

    trackConversationById(index: number, item: Conversation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-person-popup',
    template: ''
})
export class PersonPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personPopupService: PersonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personPopupService
                    .open(PersonDialogComponent, params['id']);
            } else {
                this.modalRef = this.personPopupService
                    .open(PersonDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
