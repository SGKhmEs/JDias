import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Tagging } from './tagging.model';
import { TaggingPopupService } from './tagging-popup.service';
import { TaggingService } from './tagging.service';
import { Tag, TagService } from '../tag';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tagging-dialog',
    templateUrl: './tagging-dialog.component.html'
})
export class TaggingDialogComponent implements OnInit {

    tagging: Tagging;
    authorities: any[];
    isSaving: boolean;

    tags: Tag[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private taggingService: TaggingService,
        private tagService: TagService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.tagService.query()
            .subscribe((res: ResponseWrapper) => { this.tags = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tagging.id !== undefined) {
            this.subscribeToSaveResponse(
                this.taggingService.update(this.tagging));
        } else {
            this.subscribeToSaveResponse(
                this.taggingService.create(this.tagging));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tagging>) {
        result.subscribe((res: Tagging) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Tagging) {
        this.eventManager.broadcast({ name: 'taggingListModification', content: 'OK'});
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

    trackTagById(index: number, item: Tag) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tagging-popup',
    template: ''
})
export class TaggingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taggingPopupService: TaggingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.taggingPopupService
                    .open(TaggingDialogComponent, params['id']);
            } else {
                this.modalRef = this.taggingPopupService
                    .open(TaggingDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
