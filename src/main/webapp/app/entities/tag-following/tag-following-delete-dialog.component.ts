import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { TagFollowing } from './tag-following.model';
import { TagFollowingPopupService } from './tag-following-popup.service';
import { TagFollowingService } from './tag-following.service';

@Component({
    selector: 'jhi-tag-following-delete-dialog',
    templateUrl: './tag-following-delete-dialog.component.html'
})
export class TagFollowingDeleteDialogComponent {

    tagFollowing: TagFollowing;

    constructor(
        private tagFollowingService: TagFollowingService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tagFollowingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tagFollowingListModification',
                content: 'Deleted an tagFollowing'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tag-following-delete-popup',
    template: ''
})
export class TagFollowingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tagFollowingPopupService: TagFollowingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.tagFollowingPopupService
                .open(TagFollowingDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
