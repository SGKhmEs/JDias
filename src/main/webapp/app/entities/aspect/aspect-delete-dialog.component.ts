import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Aspect } from './aspect.model';
import { AspectPopupService } from './aspect-popup.service';
import { AspectService } from './aspect.service';

@Component({
    selector: 'jhi-aspect-delete-dialog',
    templateUrl: './aspect-delete-dialog.component.html'
})
export class AspectDeleteDialogComponent {

    aspect: Aspect;

    constructor(
        private aspectService: AspectService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aspectService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'aspectListModification',
                content: 'Deleted an aspect'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.aspect.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-aspect-delete-popup',
    template: ''
})
export class AspectDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectPopupService: AspectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.aspectPopupService
                .open(AspectDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
