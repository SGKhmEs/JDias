import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Aspect } from './aspect.model';
import { AspectPopupService } from './aspect-popup.service';
import { AspectService } from './aspect.service';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-aspect-dialog',
    templateUrl: './aspect-dialog.component.html'
})
export class AspectDialogComponent implements OnInit {

    aspect: Aspect;
    isSaving: boolean;

    people: Person[];
    createdAtDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private aspectService: AspectService,
        private PersonService: PersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.PersonService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.aspect.id !== undefined) {
            this.subscribeToSaveResponse(
                this.aspectService.update(this.aspect));
        } else {
            this.subscribeToSaveResponse(
                this.aspectService.create(this.aspect));
        }
    }

    private subscribeToSaveResponse(result: Observable<Aspect>) {
        result.subscribe((res: Aspect) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Aspect) {
        this.eventManager.broadcast({ name: 'aspectListModification', content: 'OK'});
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

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-aspect-popup',
    template: ''
})
export class AspectPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aspectPopupService: AspectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.aspectPopupService
                    .open(AspectDialogComponent as Component, params['id']);
            } else {
                this.aspectPopupService
                    .open(AspectDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
