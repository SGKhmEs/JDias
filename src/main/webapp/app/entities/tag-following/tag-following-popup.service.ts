import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { TagFollowing } from './tag-following.model';
import { TagFollowingService } from './tag-following.service';
@Injectable()
export class TagFollowingPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private tagFollowingService: TagFollowingService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.tagFollowingService.find(id).subscribe((tagFollowing) => {
                tagFollowing.createdAt = this.datePipe
                    .transform(tagFollowing.createdAt, 'yyyy-MM-ddThh:mm');
                tagFollowing.updatedAt = this.datePipe
                    .transform(tagFollowing.updatedAt, 'yyyy-MM-ddThh:mm');
                this.tagFollowingModalRef(component, tagFollowing);
            });
        } else {
            return this.tagFollowingModalRef(component, new TagFollowing());
        }
    }

    tagFollowingModalRef(component: Component, tagFollowing: TagFollowing): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tagFollowing = tagFollowing;
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
