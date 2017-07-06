import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Aspect } from './aspect.model';
import { AspectPopupService } from './aspect-popup.service';
import { AspectService } from './aspect.service';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-aspect-dialog',
    templateUrl: './aspect-dialog.component.html'
})
export class AspectDialogComponent implements OnInit {

    aspect: Aspect;
    authorities: any[];
    isSaving: boolean;

    people: Person[];
    createdAtDp: any;
    updatedAtDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private aspectService: AspectService,
        private PersonService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.PersonService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.aspect.id !== undefined) {
            this.subscribeToSaveResponse(
                this.aspectService.update(this.aspect), false);
        } else {
            this.subscribeToSaveResponse(
                this.aspectService.create(this.aspect), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Aspect>, isCreated: boolean) {
        result.subscribe((res: Aspect) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Aspect, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.aspect.created'
            : 'jDiasApp.aspect.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'aspectListModification', content: 'OK'});
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

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-aspect-popup',
    template: ''
})
export class AspectPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectPopupService: AspectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.aspectPopupService
                    .open(AspectDialogComponent, params['id']);
            } else {
                this.modalRef = this.aspectPopupService
                    .open(AspectDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
