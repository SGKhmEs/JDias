import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Event } from './event.model';
import { EventService } from './event.service';
@Injectable()
export class EventPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private eventService: EventService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.eventService.find(id).subscribe((event) => {
                event.start = this.datePipe
                    .transform(event.start, 'yyyy-MM-ddThh:mm');
                event.end = this.datePipe
                    .transform(event.end, 'yyyy-MM-ddThh:mm');
                this.eventModalRef(component, event);
            });
        } else {
            return this.eventModalRef(component, new Event());
        }
    }

    eventModalRef(component: Component, event: Event): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.event = event;
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
