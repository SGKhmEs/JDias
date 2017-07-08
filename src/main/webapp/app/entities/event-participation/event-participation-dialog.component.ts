import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EventParticipation } from './event-participation.model';
import { EventParticipationPopupService } from './event-participation-popup.service';
import { EventParticipationService } from './event-participation.service';
import { Event, EventService } from '../event';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-event-participation-dialog',
    templateUrl: './event-participation-dialog.component.html'
})
export class EventParticipationDialogComponent implements OnInit {

    eventParticipation: EventParticipation;
    authorities: any[];
    isSaving: boolean;

    events: Event[];

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventParticipationService: EventParticipationService,
        private eventService: EventService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.eventService.query()
            .subscribe((res: ResponseWrapper) => { this.events = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.eventParticipation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.eventParticipationService.update(this.eventParticipation), false);
        } else {
            this.subscribeToSaveResponse(
                this.eventParticipationService.create(this.eventParticipation), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<EventParticipation>, isCreated: boolean) {
        result.subscribe((res: EventParticipation) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EventParticipation, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.eventParticipation.created'
            : 'jDiasApp.eventParticipation.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'eventParticipationListModification', content: 'OK'});
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

    trackEventById(index: number, item: Event) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-event-participation-popup',
    template: ''
})
export class EventParticipationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private eventParticipationPopupService: EventParticipationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.eventParticipationPopupService
                    .open(EventParticipationDialogComponent, params['id']);
            } else {
                this.modalRef = this.eventParticipationPopupService
                    .open(EventParticipationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
