import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Conversation } from './conversation.model';
import { ConversationPopupService } from './conversation-popup.service';
import { ConversationService } from './conversation.service';

@Component({
    selector: 'jhi-conversation-delete-dialog',
    templateUrl: './conversation-delete-dialog.component.html'
})
export class ConversationDeleteDialogComponent {

    conversation: Conversation;

    constructor(
        private conversationService: ConversationService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.conversationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'conversationListModification',
                content: 'Deleted an conversation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-conversation-delete-popup',
    template: ''
})
export class ConversationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private conversationPopupService: ConversationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.conversationPopupService
                .open(ConversationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
