import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PollAnswer } from './poll-answer.model';
import { PollAnswerPopupService } from './poll-answer-popup.service';
import { PollAnswerService } from './poll-answer.service';

@Component({
    selector: 'jhi-poll-answer-delete-dialog',
    templateUrl: './poll-answer-delete-dialog.component.html'
})
export class PollAnswerDeleteDialogComponent {

    pollAnswer: PollAnswer;

    constructor(
        private pollAnswerService: PollAnswerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pollAnswerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pollAnswerListModification',
                content: 'Deleted an pollAnswer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-poll-answer-delete-popup',
    template: ''
})
export class PollAnswerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pollAnswerPopupService: PollAnswerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pollAnswerPopupService
                .open(PollAnswerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
