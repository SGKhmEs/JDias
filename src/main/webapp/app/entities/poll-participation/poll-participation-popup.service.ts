import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PollParticipation } from './poll-participation.model';
import { PollParticipationService } from './poll-participation.service';
@Injectable()
export class PollParticipationPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pollParticipationService: PollParticipationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.pollParticipationService.find(id).subscribe((pollParticipation) => {
                this.pollParticipationModalRef(component, pollParticipation);
            });
        } else {
            return this.pollParticipationModalRef(component, new PollParticipation());
        }
    }

    pollParticipationModalRef(component: Component, pollParticipation: PollParticipation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pollParticipation = pollParticipation;
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
