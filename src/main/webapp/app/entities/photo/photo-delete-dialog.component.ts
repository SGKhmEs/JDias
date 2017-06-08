import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Photo } from './photo.model';
import { PhotoPopupService } from './photo-popup.service';
import { PhotoService } from './photo.service';

@Component({
    selector: 'jhi-photo-delete-dialog',
    templateUrl: './photo-delete-dialog.component.html'
})
export class PhotoDeleteDialogComponent {

    photo: Photo;

    constructor(
        private photoService: PhotoService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.photoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'photoListModification',
                content: 'Deleted an photo'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.photo.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-photo-delete-popup',
    template: ''
})
export class PhotoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private photoPopupService: PhotoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.photoPopupService
                .open(PhotoDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
