import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { StatusMessage, StatusMessageDTO } from './status-message.model';
import { StatusMessageService } from './status-message.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {LocationDialogComponent} from '../location/location-dialog.component';
import {LocationService} from '../location/location.service';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {Location} from '../location/location.model';
import {Observable} from 'rxjs/Observable';
import {Poll} from '../poll/poll.model';
import {PollAnswer} from '../poll-answer/poll-answer.model';
import {PollService} from '../poll/poll.service';
import {PollAnswerService} from '../poll-answer/poll-answer.service';

@Component({
    selector: 'jhi-status-message',
    templateUrl: './status-message.component.html',
    styleUrls: ['../../../content/scss/sm.css'],
    providers: [LocationService]
})

export class StatusMessageComponent implements OnInit, OnDestroy {

    //#region Variables
    statusM: StatusMessage = new StatusMessage();
    statusMessage: StatusMessageDTO = new StatusMessageDTO();
    poll: Poll = new Poll;
    pollAnswers: PollAnswer[] = [new PollAnswer, new PollAnswer];
    inputAnswers: string[] = new Array(2);
    statusMessages: StatusMessage[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    isLocation = true;
    isPhoto = true;
    isPoll = true;
    locationCoord: string;
    location: Location = new Location();
    isSaving: boolean;
    isShareDisabled = false;
    options = {
        enableHighAccuracy: true,
        timeout: 3000,
        maximumAge: 0
    };

    //#endregion

    //#region Constructor
    constructor(
        // public activeModal: NgbActiveModal,
        private statusMessageService: StatusMessageService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private locationService: LocationService,
        private pollService: PollService,
        private pollAnswerService: PollAnswerService
) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }
    //#endregion

    //#region Location
    removeLocation() {
        this.locationService.delete(this.location.id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'statusMessageListModification',
                content: 'Deleted an statusMessage'
            });
        });
        this.alertService.success('jDiasApp.statusMessage.deleted', { param : this.location.id }, null);
        this.location.id = null;
        this.locationCoord = null;
        // this.statusMessage.location = null;
        this.isLocation = true;
    }

    getLocation() {
        navigator.geolocation.getCurrentPosition(this.successCallback, this.errorCallback, this.options);
    }

    successCallback = (position) => {
        this.location.lat = position.coords.latitude;
        this.location.lng = position.coords.longitude;
        console.log(this.location.lng, this.location.lat);
        this.subscribeToSaveResponse(
            this.locationService.create(this.location), false);
        // this.locationService.create(this.location);
    }

    errorCallback = (error) => {
        let errorMessage = 'Unknown error';
        switch (error.code) {
            case 1:
                errorMessage = 'Permission denied';
                break;
            case 2:
                errorMessage = 'Position unavailable';
                break;
            case 3:
                errorMessage = 'Timeout';
                break;
        }
        console.log(errorMessage);
    }

    private subscribeToSaveResponse(result: Observable<Location>, isCreated: boolean) {
        result.subscribe((res: Location) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Location, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.location.created'
                : 'jDiasApp.location.updated',
            { param : result.id }, null);

        this.locationService.find(result.id).subscribe((location) => {
            this.location = location;
            this.location.id = result.id;
            this.locationCoord = this.location.address;
            this.isLocation = false;
            // this.statusMessage.location = this.location;
        });

        this.eventManager.broadcast({ name: 'locationListModification', content: 'OK'});
        this.isSaving = false;
    }
//#endregion

    //#regionStatusMessage

    saveStatusMessage() {
        // this.savePoll();
        /*for (const answer of this.pollAnswers) {
            answer.poll = this.poll;
            this.savePollAnswer(answer);
        }*/
        this.statusMessage.location_address = this.location.address;
        this.statusMessage.poll_question = this.poll.question;
        this.statusMessage.poll_answers = [this.pollAnswers[0].answer, this.pollAnswers[1].answer];
        this.statusMessage.location_coords = this.location.lng + ', ' + this.location.lat;
        this.statusMessage.photos = [];
        this.statusMessage.aspect_ids = [];
        this.statusM.location = this.location;
        this.statusMessage.status_message = this.statusM;
        this.isSaving = true;
       // this.statusMessage.text = 'test';
        if (this.statusMessage.id !== undefined) {
            this.subscribeToSaveStatusMessageResponse(
                this.statusMessageService.update(this.statusMessage), false);
        } else {
            this.subscribeToSaveStatusMessageResponse(
                this.statusMessageService.create(this.statusMessage), true);
        }
    }

    private subscribeToSaveStatusMessageResponse(result: Observable<StatusMessageDTO>, isCreated: boolean) {
        result.subscribe((res: StatusMessageDTO) =>
            this.onSaveStatusMessageSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveStatusMessageSuccess(result: StatusMessageDTO, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.statusMessage.created'
                : 'jDiasApp.statusMessage.updated',
            { param : result.id }, null);
        this.statusMessageService.find(result.id).subscribe((statusmessage) => {});
        this.eventManager.broadcast({ name: 'statusMessageListModification', content: 'OK'});
        this.isSaving = false;
    }

    //#endregion

    //#region Poll Answers

    savePollAnswer(pollAnswer: PollAnswer) {
        this.isSaving = true;
        if (pollAnswer.id !== undefined) {
            this.subscribeToSavePollAnswerResponse(
                this.pollAnswerService.update(pollAnswer), false);
        } else {
            this.subscribeToSavePollAnswerResponse(
                this.pollAnswerService.create(pollAnswer), true);
        }
    }

    private subscribeToSavePollAnswerResponse(result: Observable<PollAnswer>, isCreated: boolean) {
        result.subscribe((res: PollAnswer) =>
            this.onSavePollAnswerSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSavePollAnswerSuccess(result: PollAnswer, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.pollAnswer.created'
                : 'jDiasApp.pollAnswer.updated',
            { param : result.id }, null);
        this.pollAnswerService.find(result.id).subscribe((polanswer) => {});
        this.eventManager.broadcast({ name: 'pollAnswerListModification', content: 'OK'});
        this.isSaving = false;
    }

    //#endregion

    //#region Poll Question

    createPoll() {
        this.isPoll = !this.isPoll;
    }

    savePoll() {
        this.isSaving = true;
        if (this.poll.id !== undefined) {
            this.subscribeToSavePollResponse(
                this.pollService.update(this.poll), false);
        } else {
            this.subscribeToSavePollResponse(
                this.pollService.create(this.poll), true);
        }
    }

    private subscribeToSavePollResponse(result: Observable<Poll>, isCreated: boolean) {
        result.subscribe((res: Poll) =>
            this.onSavePollSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSavePollSuccess(result: Poll, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.poll.created'
                : 'jDiasApp.poll.updated',
            { param : result.id }, null);
        this.pollService.find(result.id).subscribe((poll) => {
            this.poll.id = result.id;
            // this.statusMessage.poll = this.poll;
        });
        this.eventManager.broadcast({ name: 'pollListModification', content: 'OK'});
        this.isSaving = false;
    }

    //#endregion

    loadAll() {
        if (this.currentSearch) {
            this.statusMessageService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.statusMessages = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.statusMessageService.query().subscribe(
            (res: ResponseWrapper) => {
                this.statusMessages = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.isSaving = false;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInStatusMessages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: StatusMessage) {
        return item.id;
    }

    registerChangeInStatusMessages() {
        this.eventSubscriber = this.eventManager.subscribe('statusMessageListModification', (response) => this.loadAll());
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
