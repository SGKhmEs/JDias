import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Poll } from './poll.model';
import { PollPopupService } from './poll-popup.service';
import { PollService } from './poll.service';

@Component({
    selector: 'jhi-poll-dialog',
    templateUrl: './poll-dialog.component.html'
})
export class PollDialogComponent implements OnInit {

    poll: Poll;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private pollService: PollService,
        private eventManager: EventManager
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
        if (this.poll.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pollService.update(this.poll));
        } else {
            this.subscribeToSaveResponse(
                this.pollService.create(this.poll));
        }
    }

    private subscribeToSaveResponse(result: Observable<Poll>) {
        result.subscribe((res: Poll) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Poll) {
        this.eventManager.broadcast({ name: 'pollListModification', content: 'OK'});
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
    selector: 'jhi-poll-popup',
    template: ''
})
export class PollPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pollPopupService: PollPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.pollPopupService
                    .open(PollDialogComponent, params['id']);
            } else {
                this.modalRef = this.pollPopupService
                    .open(PollDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
