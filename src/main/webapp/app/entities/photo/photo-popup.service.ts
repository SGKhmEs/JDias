import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Photo } from './photo.model';
import { PhotoService } from './photo.service';
@Injectable()
export class PhotoPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private photoService: PhotoService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.photoService.find(id).subscribe((photo) => {
                photo.createdAt = this.datePipe
                    .transform(photo.createdAt, 'yyyy-MM-ddThh:mm');
                this.photoModalRef(component, photo);
            });
        } else {
            return this.photoModalRef(component, new Photo());
        }
    }

    photoModalRef(component: Component, photo: Photo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.photo = photo;
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
