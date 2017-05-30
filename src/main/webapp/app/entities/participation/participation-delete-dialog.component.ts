import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Participation } from './participation.model';
import { ParticipationPopupService } from './participation-popup.service';
import { ParticipationService } from './participation.service';

@Component({
    selector: 'jhi-participation-delete-dialog',
    templateUrl: './participation-delete-dialog.component.html'
})
export class ParticipationDeleteDialogComponent {

    participation: Participation;

    constructor(
        private participationService: ParticipationService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.participationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'participationListModification',
                content: 'Deleted an participation'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.participation.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-participation-delete-popup',
    template: ''
})
export class ParticipationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private participationPopupService: ParticipationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.participationPopupService
                .open(ParticipationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
