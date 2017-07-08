import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Like } from './like.model';
import { LikePopupService } from './like-popup.service';
import { LikeService } from './like.service';

@Component({
    selector: 'jhi-like-delete-dialog',
    templateUrl: './like-delete-dialog.component.html'
})
export class LikeDeleteDialogComponent {

    like: Like;

    constructor(
        private likeService: LikeService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.likeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'likeListModification',
                content: 'Deleted an like'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.like.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-like-delete-popup',
    template: ''
})
export class LikeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private likePopupService: LikePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.likePopupService
                .open(LikeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
