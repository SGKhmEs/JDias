import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { AspectVisiblity } from './aspect-visiblity.model';
import { AspectVisiblityPopupService } from './aspect-visiblity-popup.service';
import { AspectVisiblityService } from './aspect-visiblity.service';

@Component({
    selector: 'jhi-aspect-visiblity-delete-dialog',
    templateUrl: './aspect-visiblity-delete-dialog.component.html'
})
export class AspectVisiblityDeleteDialogComponent {

    aspectVisiblity: AspectVisiblity;

    constructor(
        private aspectVisiblityService: AspectVisiblityService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aspectVisiblityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'aspectVisiblityListModification',
                content: 'Deleted an aspectVisiblity'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('jDiasApp.aspectVisiblity.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-aspect-visiblity-delete-popup',
    template: ''
})
export class AspectVisiblityDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectVisiblityPopupService: AspectVisiblityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.aspectVisiblityPopupService
                .open(AspectVisiblityDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
