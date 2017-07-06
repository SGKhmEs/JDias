import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Aspectvisibility } from './aspectvisibility.model';
import { AspectvisibilityPopupService } from './aspectvisibility-popup.service';
import { AspectvisibilityService } from './aspectvisibility.service';

@Component({
    selector: 'jhi-aspectvisibility-delete-dialog',
    templateUrl: './aspectvisibility-delete-dialog.component.html'
})
export class AspectvisibilityDeleteDialogComponent {

    aspectvisibility: Aspectvisibility;

    constructor(
        private aspectvisibilityService: AspectvisibilityService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aspectvisibilityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'aspectvisibilityListModification',
                content: 'Deleted an aspectvisibility'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.aspectvisibility.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-aspectvisibility-delete-popup',
    template: ''
})
export class AspectvisibilityDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectvisibilityPopupService: AspectvisibilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.aspectvisibilityPopupService
                .open(AspectvisibilityDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
