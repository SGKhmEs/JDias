import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Reshare } from './reshare.model';
import { ResharePopupService } from './reshare-popup.service';
import { ReshareService } from './reshare.service';

@Component({
    selector: 'jhi-reshare-dialog',
    templateUrl: './reshare-dialog.component.html'
})
export class ReshareDialogComponent implements OnInit {

    reshare: Reshare;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private reshareService: ReshareService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reshare.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reshareService.update(this.reshare), false);
        } else {
            this.subscribeToSaveResponse(
                this.reshareService.create(this.reshare), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Reshare>, isCreated: boolean) {
        result.subscribe((res: Reshare) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Reshare, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.reshare.created'
            : 'jDiasApp.reshare.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'reshareListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-reshare-popup',
    template: ''
})
export class ResharePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resharePopupService: ResharePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.resharePopupService
                    .open(ReshareDialogComponent, params['id']);
            } else {
                this.modalRef = this.resharePopupService
                    .open(ReshareDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
