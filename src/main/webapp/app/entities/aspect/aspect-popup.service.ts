import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Aspect } from './aspect.model';
import { AspectService } from './aspect.service';

@Injectable()
export class AspectPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private aspectService: AspectService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

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
                this.aspectModalRef(component, aspect);
            });
        } else {
            return this.aspectModalRef(component, new Aspect());
        }
    }

    aspectModalRef(component: Component, aspect: Aspect): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.aspect = aspect;
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
