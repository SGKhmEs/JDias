import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Comment } from './comment.model';
import { CommentService } from './comment.service';

@Injectable()
export class CommentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private commentService: CommentService

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
                this.commentService.find(id).subscribe((comment) => {
                    if (comment.createdAt) {
                        comment.createdAt = {
                            year: comment.createdAt.getFullYear(),
                            month: comment.createdAt.getMonth() + 1,
                            day: comment.createdAt.getDate()
                        };
                    }
                    this.ngbModalRef = this.commentModalRef(component, comment);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.commentModalRef(component, new Comment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    commentModalRef(component: Component, comment: Comment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.comment = comment;
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
