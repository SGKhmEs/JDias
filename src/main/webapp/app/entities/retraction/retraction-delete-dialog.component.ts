import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Retraction } from './retraction.model';
import { RetractionPopupService } from './retraction-popup.service';
import { RetractionService } from './retraction.service';

@Component({
    selector: 'jhi-retraction-delete-dialog',
    templateUrl: './retraction-delete-dialog.component.html'
})
export class RetractionDeleteDialogComponent {

    retraction: Retraction;

    constructor(
        private retractionService: RetractionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.retractionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'retractionListModification',
                content: 'Deleted an retraction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-retraction-delete-popup',
    template: ''
})
export class RetractionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private retractionPopupService: RetractionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.retractionPopupService
                .open(RetractionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
