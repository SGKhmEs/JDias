import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PollAnswer } from './poll-answer.model';
import { PollAnswerPopupService } from './poll-answer-popup.service';
import { PollAnswerService } from './poll-answer.service';
import { Poll, PollService } from '../poll';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-poll-answer-dialog',
    templateUrl: './poll-answer-dialog.component.html'
})
export class PollAnswerDialogComponent implements OnInit {

    pollAnswer: PollAnswer;
    authorities: any[];
    isSaving: boolean;

    polls: Poll[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private pollAnswerService: PollAnswerService,
        private pollService: PollService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.pollService.query()
            .subscribe((res: ResponseWrapper) => { this.polls = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pollAnswer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pollAnswerService.update(this.pollAnswer), false);
        } else {
            this.subscribeToSaveResponse(
                this.pollAnswerService.create(this.pollAnswer), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PollAnswer>, isCreated: boolean) {
        result.subscribe((res: PollAnswer) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PollAnswer, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.pollAnswer.created'
            : 'jDiasApp.pollAnswer.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'pollAnswerListModification', content: 'OK'});
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

    trackPollById(index: number, item: Poll) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-poll-answer-popup',
    template: ''
})
export class PollAnswerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pollAnswerPopupService: PollAnswerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.pollAnswerPopupService
                    .open(PollAnswerDialogComponent, params['id']);
            } else {
                this.modalRef = this.pollAnswerPopupService
                    .open(PollAnswerDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
