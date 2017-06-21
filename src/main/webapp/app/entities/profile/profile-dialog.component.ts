import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Profile } from './profile.model';
import { ProfilePopupService } from './profile-popup.service';
import { ProfileService } from './profile.service';

@Component({
    selector: 'jhi-profile-dialog',
    templateUrl: './profile-dialog.component.html'
})
export class ProfileDialogComponent implements OnInit {

    profile: Profile;
    authorities: any[];
    isSaving: boolean;
    birthdayDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private profileService: ProfileService,
        private eventManager: JhiEventManager
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
        if (this.profile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.profileService.update(this.profile), false);
        } else {
            this.subscribeToSaveResponse(
                this.profileService.create(this.profile), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Profile>, isCreated: boolean) {
        result.subscribe((res: Profile) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Profile, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.profile.created'
            : 'jDiasApp.profile.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'profileListModification', content: 'OK'});
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
    selector: 'jhi-profile-popup',
    template: ''
})
export class ProfilePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profilePopupService: ProfilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.profilePopupService
                    .open(ProfileDialogComponent, params['id']);
            } else {
                this.modalRef = this.profilePopupService
                    .open(ProfileDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
