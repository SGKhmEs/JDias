import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Reshare } from './reshare.model';
import { ResharePopupService } from './reshare-popup.service';
import { ReshareService } from './reshare.service';

@Component({
    selector: 'jhi-reshare-delete-dialog',
    templateUrl: './reshare-delete-dialog.component.html'
})
export class ReshareDeleteDialogComponent {

    reshare: Reshare;

    constructor(
        private reshareService: ReshareService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reshareService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reshareListModification',
                content: 'Deleted an reshare'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.reshare.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-reshare-delete-popup',
    template: ''
})
export class ReshareDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resharePopupService: ResharePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.resharePopupService
                .open(ReshareDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
