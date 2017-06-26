import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Interaction } from './interaction.model';
import { InteractionPopupService } from './interaction-popup.service';
import { InteractionService } from './interaction.service';

@Component({
    selector: 'jhi-interaction-dialog',
    templateUrl: './interaction-dialog.component.html'
})
export class InteractionDialogComponent implements OnInit {

    interaction: Interaction;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private interactionService: InteractionService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['interaction']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.interaction.id !== undefined) {
            this.interactionService.update(this.interaction)
                .subscribe((res: Interaction) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.interactionService.create(this.interaction)
                .subscribe((res: Interaction) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: Interaction) {
        this.eventManager.broadcast({ name: 'interactionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-interaction-popup',
    template: ''
})
export class InteractionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private interactionPopupService: InteractionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.interactionPopupService
                    .open(InteractionDialogComponent, params['id']);
            } else {
                this.modalRef = this.interactionPopupService
                    .open(InteractionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
