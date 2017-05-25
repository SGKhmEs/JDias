import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PollParticipation } from './poll-participation.model';
import { PollParticipationPopupService } from './poll-participation-popup.service';
import { PollParticipationService } from './poll-participation.service';
import { Poll, PollService } from '../poll';
import { PollAnswer, PollAnswerService } from '../poll-answer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-poll-participation-dialog',
    templateUrl: './poll-participation-dialog.component.html'
})
export class PollParticipationDialogComponent implements OnInit {

    pollParticipation: PollParticipation;
    authorities: any[];
    isSaving: boolean;

    polls: Poll[];

    pollanswers: PollAnswer[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private pollParticipationService: PollParticipationService,
        private pollService: PollService,
        private pollAnswerService: PollAnswerService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.pollService.query()
            .subscribe((res: ResponseWrapper) => { this.polls = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.pollAnswerService.query()
            .subscribe((res: ResponseWrapper) => { this.pollanswers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pollParticipation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pollParticipationService.update(this.pollParticipation));
        } else {
            this.subscribeToSaveResponse(
                this.pollParticipationService.create(this.pollParticipation));
        }
    }

    private subscribeToSaveResponse(result: Observable<PollParticipation>) {
        result.subscribe((res: PollParticipation) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PollParticipation) {
        this.eventManager.broadcast({ name: 'pollParticipationListModification', content: 'OK'});
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

    trackPollAnswerById(index: number, item: PollAnswer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-poll-participation-popup',
    template: ''
})
export class PollParticipationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pollParticipationPopupService: PollParticipationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.pollParticipationPopupService
                    .open(PollParticipationDialogComponent, params['id']);
            } else {
                this.modalRef = this.pollParticipationPopupService
                    .open(PollParticipationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
