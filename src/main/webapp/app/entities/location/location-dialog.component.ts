import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Location } from './location.model';
import { LocationPopupService } from './location-popup.service';
import { LocationService } from './location.service';

@Component({
    selector: 'jhi-location-dialog',
    templateUrl: './location-dialog.component.html'
})
export class LocationDialogComponent implements OnInit {

    location: Location;
    authorities: any[];
    isSaving: boolean;

    options = {
        enableHighAccuracy: true,
        timeout: 1000,
        maximumAge: 0
    };

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private locationService: LocationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        navigator.geolocation.getCurrentPosition(this.successCallback, this.errorCallback, this.options);
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.location.id !== undefined) {
            this.subscribeToSaveResponse(
                this.locationService.update(this.location), false);
        } else {
            this.subscribeToSaveResponse(
                this.locationService.create(this.location), true);
        }
    }

    /*saveLocation(location: Location) {
        this.isSaving = true;
        if (location.id !== undefined) {
            this.subscribeToSaveResponse(
                this.locationService.update(location), false);
        } else {
            this.subscribeToSaveResponse(
                this.locationService.create(location), true);
        }
    }*/

    private subscribeToSaveResponse(result: Observable<Location>, isCreated: boolean) {
        result.subscribe((res: Location) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Location, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.location.created'
            : 'jDiasApp.location.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'locationListModification', content: 'OK'});
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

    successCallback = (position) => {
        this.location.lat = position.coords.latitude;
        this.location.lng = position.coords.longitude;
    }

    errorCallback = (error) => {
        let errorMessage = 'Unknown error';
        switch (error.code) {
            case 1:
                errorMessage = 'Permission denied';
                break;
            case 2:
                errorMessage = 'Position unavailable';
                break;
            case 3:
                errorMessage = 'Timeout';
                break;
        }
        console.log(errorMessage);
    }
}

@Component({
    selector: 'jhi-location-popup',
    template: ''
})
export class LocationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationPopupService: LocationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.locationPopupService
                    .open(LocationDialogComponent, params['id']);
            } else {
                this.modalRef = this.locationPopupService
                    .open(LocationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
