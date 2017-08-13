import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Aspect } from './aspect.model';
import { AspectService } from './aspect.service';

@Injectable()
export class AspectPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private aspectService: AspectService

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
                this.aspectService.find(id).subscribe((aspect) => {
                    if (aspect.createdAt) {
                        aspect.createdAt = {
                            year: aspect.createdAt.getFullYear(),
                            month: aspect.createdAt.getMonth() + 1,
                            day: aspect.createdAt.getDate()
                        };
                    }
                    aspect.updatedAt = this.datePipe
                        .transform(aspect.updatedAt, 'yyyy-MM-ddThh:mm');
                    this.ngbModalRef = this.aspectModalRef(component, aspect);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.aspectModalRef(component, new Aspect());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    aspectModalRef(component: Component, aspect: Aspect): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.aspect = aspect;
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
