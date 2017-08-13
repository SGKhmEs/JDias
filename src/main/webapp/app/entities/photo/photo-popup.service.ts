import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Photo } from './photo.model';
import { PhotoService } from './photo.service';

@Injectable()
export class PhotoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private photoService: PhotoService

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
                this.photoService.find(id).subscribe((photo) => {
                    if (photo.createdAt) {
                        photo.createdAt = {
                            year: photo.createdAt.getFullYear(),
                            month: photo.createdAt.getMonth() + 1,
                            day: photo.createdAt.getDate()
                        };
                    }
                    this.ngbModalRef = this.photoModalRef(component, photo);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.photoModalRef(component, new Photo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    photoModalRef(component: Component, photo: Photo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.photo = photo;
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
