import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

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
        private eventManager: JhiEventManager
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
    }
}

@Component({
    selector: 'jhi-event-participation-delete-popup',
    template: ''
})
export class EventParticipationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private eventParticipationPopupService: EventParticipationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.eventParticipationPopupService
                .open(EventParticipationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
