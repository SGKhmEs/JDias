import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Aspectvisibility } from './aspectvisibility.model';
import { AspectvisibilityPopupService } from './aspectvisibility-popup.service';
import { AspectvisibilityService } from './aspectvisibility.service';

@Component({
    selector: 'jhi-aspectvisibility-dialog',
    templateUrl: './aspectvisibility-dialog.component.html'
})
export class AspectvisibilityDialogComponent implements OnInit {

    aspectvisibility: Aspectvisibility;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private aspectvisibilityService: AspectvisibilityService,
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
        if (this.aspectvisibility.id !== undefined) {
            this.subscribeToSaveResponse(
                this.aspectvisibilityService.update(this.aspectvisibility), false);
        } else {
            this.subscribeToSaveResponse(
                this.aspectvisibilityService.create(this.aspectvisibility), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Aspectvisibility>, isCreated: boolean) {
        result.subscribe((res: Aspectvisibility) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Aspectvisibility, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.aspectvisibility.created'
            : 'jDiasApp.aspectvisibility.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'aspectvisibilityListModification', content: 'OK'});
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
    selector: 'jhi-aspectvisibility-popup',
    template: ''
})
export class AspectvisibilityPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectvisibilityPopupService: AspectvisibilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.aspectvisibilityPopupService
                    .open(AspectvisibilityDialogComponent, params['id']);
            } else {
                this.modalRef = this.aspectvisibilityPopupService
                    .open(AspectvisibilityDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
