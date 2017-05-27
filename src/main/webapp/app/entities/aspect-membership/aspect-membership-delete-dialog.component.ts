import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AspectMembership } from './aspect-membership.model';
import { AspectMembershipPopupService } from './aspect-membership-popup.service';
import { AspectMembershipService } from './aspect-membership.service';

@Component({
    selector: 'jhi-aspect-membership-delete-dialog',
    templateUrl: './aspect-membership-delete-dialog.component.html'
})
export class AspectMembershipDeleteDialogComponent {

    aspectMembership: AspectMembership;

    constructor(
        private aspectMembershipService: AspectMembershipService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aspectMembershipService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'aspectMembershipListModification',
                content: 'Deleted an aspectMembership'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-aspect-membership-delete-popup',
    template: ''
})
export class AspectMembershipDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectMembershipPopupService: AspectMembershipPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.aspectMembershipPopupService
                .open(AspectMembershipDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
