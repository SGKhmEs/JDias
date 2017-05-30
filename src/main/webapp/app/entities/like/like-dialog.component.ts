import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Like } from './like.model';
import { LikePopupService } from './like-popup.service';
import { LikeService } from './like.service';
import { Post, PostService } from '../post';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-like-dialog',
    templateUrl: './like-dialog.component.html'
})
export class LikeDialogComponent implements OnInit {

    like: Like;
    authorities: any[];
    isSaving: boolean;

    posts: Post[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private likeService: LikeService,
        private postService: PostService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.postService.query()
            .subscribe((res: ResponseWrapper) => { this.posts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.like.id !== undefined) {
            this.subscribeToSaveResponse(
                this.likeService.update(this.like), false);
        } else {
            this.subscribeToSaveResponse(
                this.likeService.create(this.like), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Like>, isCreated: boolean) {
        result.subscribe((res: Like) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Like, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.like.created'
            : 'jDiasApp.like.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'likeListModification', content: 'OK'});
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

    trackPostById(index: number, item: Post) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-like-popup',
    template: ''
})
export class LikePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private likePopupService: LikePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.likePopupService
                    .open(LikeDialogComponent, params['id']);
            } else {
                this.modalRef = this.likePopupService
                    .open(LikeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
