import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AspectMembership } from './aspect-membership.model';
import { AspectMembershipService } from './aspect-membership.service';
@Injectable()
export class AspectMembershipPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private aspectMembershipService: AspectMembershipService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.aspectMembershipService.find(id).subscribe((aspectMembership) => {
                aspectMembership.createdAt = this.datePipe
                    .transform(aspectMembership.createdAt, 'yyyy-MM-ddThh:mm');
                aspectMembership.updatedAt = this.datePipe
                    .transform(aspectMembership.updatedAt, 'yyyy-MM-ddThh:mm');
                this.aspectMembershipModalRef(component, aspectMembership);
            });
        } else {
            return this.aspectMembershipModalRef(component, new AspectMembership());
        }
    }

    aspectMembershipModalRef(component: Component, aspectMembership: AspectMembership): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.aspectMembership = aspectMembership;
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
