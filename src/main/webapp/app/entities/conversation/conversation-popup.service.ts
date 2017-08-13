import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Conversation } from './conversation.model';
import { ConversationService } from './conversation.service';

@Injectable()
export class ConversationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private conversationService: ConversationService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

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
                    this.ngbModalRef = this.conversationModalRef(component, conversation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.conversationModalRef(component, new Conversation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    conversationModalRef(component: Component, conversation: Conversation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.conversation = conversation;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
