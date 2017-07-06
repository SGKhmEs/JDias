import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Photo } from './photo.model';
import { PhotoService } from './photo.service';

@Component({
    selector: 'jhi-photo-detail',
    templateUrl: './photo-detail.component.html'
})
export class PhotoDetailComponent implements OnInit, OnDestroy {

    photo: Photo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private photoService: PhotoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPhotos();
    }

    load(id) {
        this.photoService.find(id).subscribe((photo) => {
            this.photo = photo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPhotos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'photoListModification',
            (response) => this.load(this.photo.id)
        );
    }
}
