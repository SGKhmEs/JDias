import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tagging } from './tagging.model';
import { TaggingPopupService } from './tagging-popup.service';
import { TaggingService } from './tagging.service';

@Component({
    selector: 'jhi-tagging-delete-dialog',
    templateUrl: './tagging-delete-dialog.component.html'
})
export class TaggingDeleteDialogComponent {

    tagging: Tagging;

    constructor(
        private taggingService: TaggingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.taggingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'taggingListModification',
                content: 'Deleted an tagging'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tagging-delete-popup',
    template: ''
})
export class TaggingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private taggingPopupService: TaggingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.taggingPopupService
                .open(TaggingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
