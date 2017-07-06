import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PollAnswerDetailComponent } from '../../../../../../main/webapp/app/entities/poll-answer/poll-answer-detail.component';
import { PollAnswerService } from '../../../../../../main/webapp/app/entities/poll-answer/poll-answer.service';
import { PollAnswer } from '../../../../../../main/webapp/app/entities/poll-answer/poll-answer.model';

describe('Component Tests', () => {

    describe('PollAnswer Management Detail Component', () => {
        let comp: PollAnswerDetailComponent;
        let fixture: ComponentFixture<PollAnswerDetailComponent>;
        let service: PollAnswerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [PollAnswerDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PollAnswerService,
                    JhiEventManager
                ]
            }).overrideTemplate(PollAnswerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PollAnswerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PollAnswerService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PollAnswer(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pollAnswer).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
