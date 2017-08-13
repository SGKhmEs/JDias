import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PollAnswer } from './poll-answer.model';
import { PollAnswerService } from './poll-answer.service';

@Component({
    selector: 'jhi-poll-answer-detail',
    templateUrl: './poll-answer-detail.component.html'
})
export class PollAnswerDetailComponent implements OnInit, OnDestroy {

    pollAnswer: PollAnswer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pollAnswerService: PollAnswerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPollAnswers();
    }

    load(id) {
        this.pollAnswerService.find(id).subscribe((pollAnswer) => {
            this.pollAnswer = pollAnswer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPollAnswers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pollAnswerListModification',
            (response) => this.load(this.pollAnswer.id)
        );
    }
}
