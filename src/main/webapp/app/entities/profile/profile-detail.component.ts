import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Profile } from './profile.model';
import { ProfileService } from './profile.service';

@Component({
    selector: 'jhi-profile-detail',
    templateUrl: './profile-detail.component.html'
})
export class ProfileDetailComponent implements OnInit, OnDestroy {

    profile: Profile;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private profileService: ProfileService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProfiles();
    }

    load(id) {
        this.profileService.find(id).subscribe((profile) => {
            this.profile = profile;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProfiles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'profileListModification',
            (response) => this.load(this.profile.id)
        );
    }
}
