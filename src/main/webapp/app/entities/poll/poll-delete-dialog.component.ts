import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Poll } from './poll.model';
import { PollPopupService } from './poll-popup.service';
import { PollService } from './poll.service';

@Component({
    selector: 'jhi-poll-delete-dialog',
    templateUrl: './poll-delete-dialog.component.html'
})
export class PollDeleteDialogComponent {

    poll: Poll;

    constructor(
        private pollService: PollService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pollService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pollListModification',
                content: 'Deleted an poll'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.poll.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-poll-delete-popup',
    template: ''
})
export class PollDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pollPopupService: PollPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.pollPopupService
                .open(PollDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
