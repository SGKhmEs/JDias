import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AspectVisiblity } from './aspect-visiblity.model';
import { AspectVisiblityService } from './aspect-visiblity.service';

@Injectable()
export class AspectVisiblityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private aspectVisiblityService: AspectVisiblityService

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
                this.aspectVisiblityService.find(id).subscribe((aspectVisiblity) => {
                    this.ngbModalRef = this.aspectVisiblityModalRef(component, aspectVisiblity);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.aspectVisiblityModalRef(component, new AspectVisiblity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    aspectVisiblityModalRef(component: Component, aspectVisiblity: AspectVisiblity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.aspectVisiblity = aspectVisiblity;
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
