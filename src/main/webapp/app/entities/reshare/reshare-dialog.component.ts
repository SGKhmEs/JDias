import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Reshare } from './reshare.model';
import { ResharePopupService } from './reshare-popup.service';
import { ReshareService } from './reshare.service';

@Component({
    selector: 'jhi-reshare-dialog',
    templateUrl: './reshare-dialog.component.html'
})
export class ReshareDialogComponent implements OnInit {

    reshare: Reshare;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private reshareService: ReshareService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reshare.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reshareService.update(this.reshare));
        } else {
            this.subscribeToSaveResponse(
                this.reshareService.create(this.reshare));
        }
    }

    private subscribeToSaveResponse(result: Observable<Reshare>) {
        result.subscribe((res: Reshare) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Reshare) {
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

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resharePopupService: ResharePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.resharePopupService
                    .open(ReshareDialogComponent as Component, params['id']);
            } else {
                this.resharePopupService
                    .open(ReshareDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
