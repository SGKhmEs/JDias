import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Retraction } from './retraction.model';
import { RetractionPopupService } from './retraction-popup.service';
import { RetractionService } from './retraction.service';

@Component({
    selector: 'jhi-retraction-dialog',
    templateUrl: './retraction-dialog.component.html'
})
export class RetractionDialogComponent implements OnInit {

    retraction: Retraction;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private retractionService: RetractionService,
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
        if (this.retraction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.retractionService.update(this.retraction));
        } else {
            this.subscribeToSaveResponse(
                this.retractionService.create(this.retraction));
        }
    }

    private subscribeToSaveResponse(result: Observable<Retraction>) {
        result.subscribe((res: Retraction) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Retraction) {
        this.eventManager.broadcast({ name: 'retractionListModification', content: 'OK'});
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
    selector: 'jhi-retraction-popup',
    template: ''
})
export class RetractionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private retractionPopupService: RetractionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.retractionPopupService
                    .open(RetractionDialogComponent as Component, params['id']);
            } else {
                this.retractionPopupService
                    .open(RetractionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
