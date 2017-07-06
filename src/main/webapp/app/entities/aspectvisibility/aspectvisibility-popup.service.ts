import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Aspectvisibility } from './aspectvisibility.model';
import { AspectvisibilityService } from './aspectvisibility.service';
@Injectable()
export class AspectvisibilityPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private aspectvisibilityService: AspectvisibilityService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.aspectvisibilityService.find(id).subscribe((aspectvisibility) => {
                this.aspectvisibilityModalRef(component, aspectvisibility);
            });
        } else {
            return this.aspectvisibilityModalRef(component, new Aspectvisibility());
        }
    }

    aspectvisibilityModalRef(component: Component, aspectvisibility: Aspectvisibility): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.aspectvisibility = aspectvisibility;
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
