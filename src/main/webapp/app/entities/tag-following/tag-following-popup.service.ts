import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TagFollowing } from './tag-following.model';
import { TagFollowingService } from './tag-following.service';
@Injectable()
export class TagFollowingPopupService {
    private isOpen = false;
    constructor(
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
                if (tagFollowing.createdAt) {
                    tagFollowing.createdAt = {
                        year: tagFollowing.createdAt.getFullYear(),
                        month: tagFollowing.createdAt.getMonth() + 1,
                        day: tagFollowing.createdAt.getDate()
                    };
                }
                if (tagFollowing.updatedAt) {
                    tagFollowing.updatedAt = {
                        year: tagFollowing.updatedAt.getFullYear(),
                        month: tagFollowing.updatedAt.getMonth() + 1,
                        day: tagFollowing.updatedAt.getDate()
                    };
                }
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
