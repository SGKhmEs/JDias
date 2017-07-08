import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PollAnswer } from './poll-answer.model';
import { PollAnswerService } from './poll-answer.service';

@Injectable()
export class PollAnswerPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pollAnswerService: PollAnswerService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.pollAnswerService.find(id).subscribe((pollAnswer) => {
                this.pollAnswerModalRef(component, pollAnswer);
            });
        } else {
            return this.pollAnswerModalRef(component, new PollAnswer());
        }
    }

    pollAnswerModalRef(component: Component, pollAnswer: PollAnswer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pollAnswer = pollAnswer;
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
