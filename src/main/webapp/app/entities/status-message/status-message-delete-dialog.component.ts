import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { StatusMessage } from './status-message.model';
import { StatusMessagePopupService } from './status-message-popup.service';
import { StatusMessageService } from './status-message.service';

@Component({
    selector: 'jhi-status-message-delete-dialog',
    templateUrl: './status-message-delete-dialog.component.html'
})
export class StatusMessageDeleteDialogComponent {

    statusMessage: StatusMessage;

    constructor(
        private statusMessageService: StatusMessageService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.statusMessageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'statusMessageListModification',
                content: 'Deleted an statusMessage'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.statusMessage.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-status-message-delete-popup',
    template: ''
})
export class StatusMessageDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statusMessagePopupService: StatusMessagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.statusMessagePopupService
                .open(StatusMessageDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
