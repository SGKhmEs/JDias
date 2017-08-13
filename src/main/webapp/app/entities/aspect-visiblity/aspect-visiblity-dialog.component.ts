import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AspectVisiblity } from './aspect-visiblity.model';
import { AspectVisiblityPopupService } from './aspect-visiblity-popup.service';
import { AspectVisiblityService } from './aspect-visiblity.service';
import { Aspect, AspectService } from '../aspect';
import { Post, PostService } from '../post';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-aspect-visiblity-dialog',
    templateUrl: './aspect-visiblity-dialog.component.html'
})
export class AspectVisiblityDialogComponent implements OnInit {

    aspectVisiblity: AspectVisiblity;
    isSaving: boolean;

    aspects: Aspect[];

    posts: Post[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private aspectVisiblityService: AspectVisiblityService,
        private aspectService: AspectService,
        private postService: PostService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.aspectService.query()
            .subscribe((res: ResponseWrapper) => { this.aspects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.postService.query()
            .subscribe((res: ResponseWrapper) => { this.posts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.aspectVisiblity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.aspectVisiblityService.update(this.aspectVisiblity));
        } else {
            this.subscribeToSaveResponse(
                this.aspectVisiblityService.create(this.aspectVisiblity));
        }
    }

    private subscribeToSaveResponse(result: Observable<AspectVisiblity>) {
        result.subscribe((res: AspectVisiblity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AspectVisiblity) {
        this.eventManager.broadcast({ name: 'aspectVisiblityListModification', content: 'OK'});
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
    selector: 'jhi-aspect-visiblity-popup',
    template: ''
})
export class AspectVisiblityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectVisiblityPopupService: AspectVisiblityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.aspectVisiblityPopupService
                    .open(AspectVisiblityDialogComponent as Component, params['id']);
            } else {
                this.aspectVisiblityPopupService
                    .open(AspectVisiblityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
