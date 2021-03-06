import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Photo } from './photo.model';
import { PhotoPopupService } from './photo-popup.service';
import { PhotoService } from './photo.service';
import { StatusMessage, StatusMessageService } from '../status-message';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-photo-dialog',
    templateUrl: './photo-dialog.component.html'
})
export class PhotoDialogComponent implements OnInit {

    photo: Photo;
    authorities: any[];
    isSaving: boolean;

    statusmessages: StatusMessage[];

    people: Person[];
    createdAtDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private photoService: PhotoService,
        private statusMessageService: StatusMessageService,
        private personService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.statusMessageService.query()
            .subscribe((res: ResponseWrapper) => { this.statusmessages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.photo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.photoService.update(this.photo), false);
        } else {
            this.subscribeToSaveResponse(
                this.photoService.create(this.photo), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Photo>, isCreated: boolean) {
        result.subscribe((res: Photo) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Photo, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.photo.created'
            : 'jDiasApp.photo.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'photoListModification', content: 'OK'});
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

    trackStatusMessageById(index: number, item: StatusMessage) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-photo-popup',
    template: ''
})
export class PhotoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private photoPopupService: PhotoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.photoPopupService
                    .open(PhotoDialogComponent, params['id']);
            } else {
                this.modalRef = this.photoPopupService
                    .open(PhotoDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
