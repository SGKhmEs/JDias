import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { AspectVisibility } from './aspect-visibility.model';
import { AspectVisibilityPopupService } from './aspect-visibility-popup.service';
import { AspectVisibilityService } from './aspect-visibility.service';

@Component({
    selector: 'jhi-aspect-visibility-delete-dialog',
    templateUrl: './aspect-visibility-delete-dialog.component.html'
})
export class AspectVisibilityDeleteDialogComponent {

    aspectVisibility: AspectVisibility;

    constructor(
        private aspectVisibilityService: AspectVisibilityService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aspectVisibilityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'aspectVisibilityListModification',
                content: 'Deleted an aspectVisibility'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.aspectVisibility.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-aspect-visibility-delete-popup',
    template: ''
})
export class AspectVisibilityDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectVisibilityPopupService: AspectVisibilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.aspectVisibilityPopupService
                .open(AspectVisibilityDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
