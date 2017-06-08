import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { EventParticipation } from './event-participation.model';
import { EventParticipationPopupService } from './event-participation-popup.service';
import { EventParticipationService } from './event-participation.service';

@Component({
    selector: 'jhi-event-participation-delete-dialog',
    templateUrl: './event-participation-delete-dialog.component.html'
})
export class EventParticipationDeleteDialogComponent {

    eventParticipation: EventParticipation;

    constructor(
        private eventParticipationService: EventParticipationService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eventParticipationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'eventParticipationListModification',
                content: 'Deleted an eventParticipation'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.eventParticipation.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-event-participation-delete-popup',
    template: ''
})
export class EventParticipationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private eventParticipationPopupService: EventParticipationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.eventParticipationPopupService
                .open(EventParticipationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
