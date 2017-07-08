import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Conversation } from './conversation.model';
import { ConversationService } from './conversation.service';

@Injectable()
export class ConversationPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private conversationService: ConversationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.conversationService.find(id).subscribe((conversation) => {
                if (conversation.createdAt) {
                    conversation.createdAt = {
                        year: conversation.createdAt.getFullYear(),
                        month: conversation.createdAt.getMonth() + 1,
                        day: conversation.createdAt.getDate()
                    };
                }
                conversation.updatedAt = this.datePipe
                    .transform(conversation.updatedAt, 'yyyy-MM-ddThh:mm');
                this.conversationModalRef(component, conversation);
            });
        } else {
            return this.conversationModalRef(component, new Conversation());
        }
    }

    conversationModalRef(component: Component, conversation: Conversation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.conversation = conversation;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
