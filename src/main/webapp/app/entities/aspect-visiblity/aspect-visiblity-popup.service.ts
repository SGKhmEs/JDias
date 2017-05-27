import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AspectVisiblity } from './aspect-visiblity.model';
import { AspectVisiblityService } from './aspect-visiblity.service';
@Injectable()
export class AspectVisiblityPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private aspectVisiblityService: AspectVisiblityService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.aspectVisiblityService.find(id).subscribe((aspectVisiblity) => {
                this.aspectVisiblityModalRef(component, aspectVisiblity);
            });
        } else {
            return this.aspectVisiblityModalRef(component, new AspectVisiblity());
        }
    }

    aspectVisiblityModalRef(component: Component, aspectVisiblity: AspectVisiblity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.aspectVisiblity = aspectVisiblity;
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
