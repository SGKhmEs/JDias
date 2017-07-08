import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { StatusMessage } from './status-message.model';
import { StatusMessageService } from './status-message.service';

@Component({
    selector: 'jhi-status-message-detail',
    templateUrl: './status-message-detail.component.html'
})
export class StatusMessageDetailComponent implements OnInit, OnDestroy {

    statusMessage: StatusMessage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private statusMessageService: StatusMessageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStatusMessages();
    }

    load(id) {
        this.statusMessageService.find(id).subscribe((statusMessage) => {
            this.statusMessage = statusMessage;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStatusMessages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'statusMessageListModification',
            (response) => this.load(this.statusMessage.id)
        );
    }
}
