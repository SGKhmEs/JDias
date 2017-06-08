import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { PollParticipation } from './poll-participation.model';
import { PollParticipationPopupService } from './poll-participation-popup.service';
import { PollParticipationService } from './poll-participation.service';

@Component({
    selector: 'jhi-poll-participation-delete-dialog',
    templateUrl: './poll-participation-delete-dialog.component.html'
})
export class PollParticipationDeleteDialogComponent {

    pollParticipation: PollParticipation;

    constructor(
        private pollParticipationService: PollParticipationService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pollParticipationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pollParticipationListModification',
                content: 'Deleted an pollParticipation'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.pollParticipation.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-poll-participation-delete-popup',
    template: ''
})
export class PollParticipationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pollParticipationPopupService: PollParticipationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.pollParticipationPopupService
                .open(PollParticipationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
