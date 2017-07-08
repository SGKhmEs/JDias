import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Post } from './post.model';
import { PostPopupService } from './post-popup.service';
import { PostService } from './post.service';
import { StatusMessage, StatusMessageService } from '../status-message';
import { Reshare, ReshareService } from '../reshare';
import { Tag, TagService } from '../tag';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-post-dialog',
    templateUrl: './post-dialog.component.html'
})
export class PostDialogComponent implements OnInit {

    post: Post;
    authorities: any[];
    isSaving: boolean;

    statusmessages: StatusMessage[];

    reshares: Reshare[];

    tags: Tag[];

    people: Person[];
    createdAtDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private postService: PostService,
        private statusMessageService: StatusMessageService,
        private reshareService: ReshareService,
        private TagService: TagService,
        private personService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.statusMessageService
            .query({filter: 'post-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.post.statusMessage || !this.post.statusMessage.id) {
                    this.statusmessages = res.json;
                } else {
                    this.statusMessageService
                        .find(this.post.statusMessage.id)
                        .subscribe((subRes: StatusMessage) => {
                            this.statusmessages = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.reshareService
            .query({filter: 'post-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.post.reshare || !this.post.reshare.id) {
                    this.reshares = res.json;
                } else {
                    this.reshareService
                        .find(this.post.reshare.id)
                        .subscribe((subRes: Reshare) => {
                            this.reshares = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.TagService.query()
            .subscribe((res: ResponseWrapper) => { this.tags = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.post.id !== undefined) {
            this.subscribeToSaveResponse(
                this.postService.update(this.post), false);
        } else {
            this.subscribeToSaveResponse(
                this.postService.create(this.post), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Post>, isCreated: boolean) {
        result.subscribe((res: Post) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Post, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.post.created'
            : 'jDiasApp.post.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'postListModification', content: 'OK'});
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

    trackStatusMessageById(index: number, item: StatusMessage) {
        return item.id;
    }

    trackReshareById(index: number, item: Reshare) {
        return item.id;
    }

    trackTagById(index: number, item: Tag) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-post-popup',
    template: ''
})
export class PostPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private postPopupService: PostPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.postPopupService
                    .open(PostDialogComponent, params['id']);
            } else {
                this.modalRef = this.postPopupService
                    .open(PostDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
