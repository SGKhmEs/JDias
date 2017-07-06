import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AspectVisibility } from './aspect-visibility.model';
import { AspectVisibilityPopupService } from './aspect-visibility-popup.service';
import { AspectVisibilityService } from './aspect-visibility.service';
import { Aspect, AspectService } from '../aspect';
import { Post, PostService } from '../post';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-aspect-visibility-dialog',
    templateUrl: './aspect-visibility-dialog.component.html'
})
export class AspectVisibilityDialogComponent implements OnInit {

    aspectVisibility: AspectVisibility;
    authorities: any[];
    isSaving: boolean;

    aspects: Aspect[];

    posts: Post[];
    createdAtDp: any;
    updatedAtDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private aspectVisibilityService: AspectVisibilityService,
        private AspectService: AspectService,
        private PostService: PostService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.AspectService.query()
            .subscribe((res: ResponseWrapper) => { this.aspects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.PostService.query()
            .subscribe((res: ResponseWrapper) => { this.posts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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

    trackAspectById(index: number, item: Aspect) {
        return item.id;
    }

    trackPostById(index: number, item: Post) {
        return item.id;
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
