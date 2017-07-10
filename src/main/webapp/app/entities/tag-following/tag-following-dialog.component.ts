import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TagFollowing } from './tag-following.model';
import { TagFollowingPopupService } from './tag-following-popup.service';
import { TagFollowingService } from './tag-following.service';
import { Tag, TagService } from '../tag';
import { Person, PersonService } from '../person';
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

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private tagFollowingService: TagFollowingService,
        private tagService: TagService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.tagService.query()
            .subscribe((res: ResponseWrapper) => { this.tags = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tagFollowing.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tagFollowingService.update(this.tagFollowing), false);
        } else {
            this.subscribeToSaveResponse(
                this.tagFollowingService.create(this.tagFollowing), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<TagFollowing>, isCreated: boolean) {
        result.subscribe((res: TagFollowing) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TagFollowing, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.tagFollowing.created'
            : 'jDiasApp.tagFollowing.updated',
            { param : result.id }, null);

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

    trackPersonById(index: number, item: Person) {
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
