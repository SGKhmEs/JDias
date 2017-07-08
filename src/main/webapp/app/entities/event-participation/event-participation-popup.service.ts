import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventParticipation } from './event-participation.model';
import { EventParticipationService } from './event-participation.service';

@Injectable()
export class EventParticipationPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private eventParticipationService: EventParticipationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.eventParticipationService.find(id).subscribe((eventParticipation) => {
                this.eventParticipationModalRef(component, eventParticipation);
            });
        } else {
            return this.eventParticipationModalRef(component, new EventParticipation());
        }
    }

    eventParticipationModalRef(component: Component, eventParticipation: EventParticipation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.eventParticipation = eventParticipation;
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
