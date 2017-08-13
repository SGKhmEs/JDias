import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HashTag } from './hash-tag.model';
import { HashTagPopupService } from './hash-tag-popup.service';
import { HashTagService } from './hash-tag.service';

@Component({
    selector: 'jhi-hash-tag-dialog',
    templateUrl: './hash-tag-dialog.component.html'
})
export class HashTagDialogComponent implements OnInit {

    hashTag: HashTag;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private hashTagService: HashTagService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.hashTag.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hashTagService.update(this.hashTag));
        } else {
            this.subscribeToSaveResponse(
                this.hashTagService.create(this.hashTag));
        }
    }

    private subscribeToSaveResponse(result: Observable<HashTag>) {
        result.subscribe((res: HashTag) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: HashTag) {
        this.eventManager.broadcast({ name: 'hashTagListModification', content: 'OK'});
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
    selector: 'jhi-hash-tag-popup',
    template: ''
})
export class HashTagPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hashTagPopupService: HashTagPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hashTagPopupService
                    .open(HashTagDialogComponent as Component, params['id']);
            } else {
                this.hashTagPopupService
                    .open(HashTagDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
