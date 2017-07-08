import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { StatusMessage } from './status-message.model';
import { StatusMessageService } from './status-message.service';

@Injectable()
export class StatusMessagePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private statusMessageService: StatusMessageService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.statusMessageService.find(id).subscribe((statusMessage) => {
                this.statusMessageModalRef(component, statusMessage);
            });
        } else {
            return this.statusMessageModalRef(component, new StatusMessage());
        }
    }

    statusMessageModalRef(component: Component, statusMessage: StatusMessage): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.statusMessage = statusMessage;
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
