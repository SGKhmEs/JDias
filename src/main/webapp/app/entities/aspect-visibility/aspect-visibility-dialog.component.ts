import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AspectVisibility } from './aspect-visibility.model';
import { AspectVisibilityPopupService } from './aspect-visibility-popup.service';
import { AspectVisibilityService } from './aspect-visibility.service';

@Component({
    selector: 'jhi-aspect-visibility-dialog',
    templateUrl: './aspect-visibility-dialog.component.html'
})
export class AspectVisibilityDialogComponent implements OnInit {

    aspectVisibility: AspectVisibility;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private aspectVisibilityService: AspectVisibilityService,
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
        if (this.aspectVisibility.id !== undefined) {
            this.subscribeToSaveResponse(
                this.aspectVisibilityService.update(this.aspectVisibility), false);
        } else {
            this.subscribeToSaveResponse(
                this.aspectVisibilityService.create(this.aspectVisibility), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<AspectVisibility>, isCreated: boolean) {
        result.subscribe((res: AspectVisibility) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AspectVisibility, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.aspectVisibility.created'
            : 'jDiasApp.aspectVisibility.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'aspectVisibilityListModification', content: 'OK'});
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
    selector: 'jhi-aspect-visibility-popup',
    template: ''
})
export class AspectVisibilityPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectVisibilityPopupService: AspectVisibilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.aspectVisibilityPopupService
                    .open(AspectVisibilityDialogComponent, params['id']);
            } else {
                this.modalRef = this.aspectVisibilityPopupService
                    .open(AspectVisibilityDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
