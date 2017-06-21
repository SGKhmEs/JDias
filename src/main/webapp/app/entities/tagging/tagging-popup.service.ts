import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Tagging } from './tagging.model';
import { TaggingService } from './tagging.service';

@Injectable()
export class TaggingPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private taggingService: TaggingService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.taggingService.find(id).subscribe((tagging) => {
                if (tagging.createdAt) {
                    tagging.createdAt = {
                        year: tagging.createdAt.getFullYear(),
                        month: tagging.createdAt.getMonth() + 1,
                        day: tagging.createdAt.getDate()
                    };
                }
                this.taggingModalRef(component, tagging);
            });
        } else {
            return this.taggingModalRef(component, new Tagging());
        }
    }

    taggingModalRef(component: Component, tagging: Tagging): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tagging = tagging;
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
