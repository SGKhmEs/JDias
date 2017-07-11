import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Avatar } from './avatar.model';
import { AvatarPopupService } from './avatar-popup.service';
import { AvatarService } from './avatar.service';

@Component({
    selector: 'jhi-avatar-dialog',
    templateUrl: './avatar-dialog.component.html'
})
export class AvatarDialogComponent implements OnInit {

    avatar: Avatar;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private avatarService: AvatarService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['avatar']);
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
        if (this.avatar.id !== undefined) {
            this.avatarService.update(this.avatar)
                .subscribe((res: Avatar) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.avatarService.create(this.avatar)
                .subscribe((res: Avatar) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: Avatar) {
        this.eventManager.broadcast({ name: 'avatarListModification', content: 'OK'});
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
    selector: 'jhi-avatar-popup',
    template: ''
})
export class AvatarPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private avatarPopupService: AvatarPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.avatarPopupService
                    .open(AvatarDialogComponent, params['id']);
            } else {
                this.modalRef = this.avatarPopupService
                    .open(AvatarDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
