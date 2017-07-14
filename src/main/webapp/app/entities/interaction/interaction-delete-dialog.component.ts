import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Interaction } from './interaction.model';
import { InteractionPopupService } from './interaction-popup.service';
import { InteractionService } from './interaction.service';

@Component({
    selector: 'jhi-interaction-delete-dialog',
    templateUrl: './interaction-delete-dialog.component.html'
})
export class InteractionDeleteDialogComponent {

    interaction: Interaction;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private interactionService: InteractionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['interaction']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.interactionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'interactionListModification',
                content: 'Deleted an interaction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-interaction-delete-popup',
    template: ''
})
export class InteractionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private interactionPopupService: InteractionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.interactionPopupService
                .open(InteractionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
