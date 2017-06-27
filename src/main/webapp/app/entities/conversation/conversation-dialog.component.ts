import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Conversation } from './conversation.model';
import { ConversationPopupService } from './conversation-popup.service';
import { ConversationService } from './conversation.service';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-conversation-dialog',
    templateUrl: './conversation-dialog.component.html'
})
export class ConversationDialogComponent implements OnInit {

    conversation: Conversation;
    authorities: any[];
    isSaving: boolean;

    people: Person[];
    createdAtDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private conversationService: ConversationService,
        private personService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.conversation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.conversationService.update(this.conversation), false);
        } else {
            this.subscribeToSaveResponse(
                this.conversationService.create(this.conversation), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Conversation>, isCreated: boolean) {
        result.subscribe((res: Conversation) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Conversation, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.conversation.created'
            : 'jDiasApp.conversation.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'conversationListModification', content: 'OK'});
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

    trackPersonById(index: number, item: Person) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-conversation-popup',
    template: ''
})
export class ConversationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private conversationPopupService: ConversationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.conversationPopupService
                    .open(ConversationDialogComponent, params['id']);
            } else {
                this.modalRef = this.conversationPopupService
                    .open(ConversationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
