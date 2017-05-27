import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { TagFollowing } from './tag-following.model';
import { TagFollowingPopupService } from './tag-following-popup.service';
import { TagFollowingService } from './tag-following.service';
import { Tag, TagService } from '../tag';
import { UserAccount, UserAccountService } from '../user-account';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tag-following-dialog',
    templateUrl: './tag-following-dialog.component.html'
})
export class TagFollowingDialogComponent implements OnInit {

    tagFollowing: TagFollowing;
    authorities: any[];
    isSaving: boolean;

    tags: Tag[];

    useraccounts: UserAccount[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private tagFollowingService: TagFollowingService,
        private tagService: TagService,
        private userAccountService: UserAccountService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.tagService.query()
            .subscribe((res: ResponseWrapper) => { this.tags = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userAccountService.query()
            .subscribe((res: ResponseWrapper) => { this.useraccounts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tagFollowing.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tagFollowingService.update(this.tagFollowing));
        } else {
            this.subscribeToSaveResponse(
                this.tagFollowingService.create(this.tagFollowing));
        }
    }

    private subscribeToSaveResponse(result: Observable<TagFollowing>) {
        result.subscribe((res: TagFollowing) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TagFollowing) {
        this.eventManager.broadcast({ name: 'tagFollowingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackTagById(index: number, item: Tag) {
        return item.id;
    }

    trackUserAccountById(index: number, item: UserAccount) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tag-following-popup',
    template: ''
})
export class TagFollowingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tagFollowingPopupService: TagFollowingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.tagFollowingPopupService
                    .open(TagFollowingDialogComponent, params['id']);
            } else {
                this.modalRef = this.tagFollowingPopupService
                    .open(TagFollowingDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
