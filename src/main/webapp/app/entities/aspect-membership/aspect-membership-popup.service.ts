import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AspectMembership } from './aspect-membership.model';
import { AspectMembershipService } from './aspect-membership.service';
@Injectable()
export class AspectMembershipPopupService {
    private isOpen = false;
    constructor(
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
                if (aspectMembership.createdAt) {
                    aspectMembership.createdAt = {
                        year: aspectMembership.createdAt.getFullYear(),
                        month: aspectMembership.createdAt.getMonth() + 1,
                        day: aspectMembership.createdAt.getDate()
                    };
                }
                if (aspectMembership.updatedAt) {
                    aspectMembership.updatedAt = {
                        year: aspectMembership.updatedAt.getFullYear(),
                        month: aspectMembership.updatedAt.getMonth() + 1,
                        day: aspectMembership.updatedAt.getDate()
                    };
                }
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
