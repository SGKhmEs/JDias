import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Post } from './post.model';
import { PostPopupService } from './post-popup.service';
import { PostService } from './post.service';
import { StatusMessage, StatusMessageService } from '../status-message';
import { Reshare, ReshareService } from '../reshare';
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

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private postService: PostService,
        private statusMessageService: StatusMessageService,
        private reshareService: ReshareService,
        private personService: PersonService,
        private eventManager: EventManager
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
                this.postService.update(this.post));
        } else {
            this.subscribeToSaveResponse(
                this.postService.create(this.post));
        }
    }

    private subscribeToSaveResponse(result: Observable<Post>) {
        result.subscribe((res: Post) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Post) {
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

    trackPersonById(index: number, item: Person) {
        return item.id;
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
