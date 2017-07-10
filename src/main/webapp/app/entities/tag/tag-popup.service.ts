import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Tag } from './tag.model';
import { TagService } from './tag.service';

@Injectable()
export class TagPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private tagService: TagService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.tagService.find(id).subscribe((tag) => {
                if (tag.createdAt) {
                    tag.createdAt = {
                        year: tag.createdAt.getFullYear(),
                        month: tag.createdAt.getMonth() + 1,
                        day: tag.createdAt.getDate()
                    };
                }
                tag.updatedAt = this.datePipe
                    .transform(tag.updatedAt, 'yyyy-MM-ddThh:mm');
                this.tagModalRef(component, tag);
            });
        } else {
            return this.tagModalRef(component, new Tag());
        }
    }

    tagModalRef(component: Component, tag: Tag): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tag = tag;
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
