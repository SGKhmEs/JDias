/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PollParticipationDetailComponent } from '../../../../../../main/webapp/app/entities/poll-participation/poll-participation-detail.component';
import { PollParticipationService } from '../../../../../../main/webapp/app/entities/poll-participation/poll-participation.service';
import { PollParticipation } from '../../../../../../main/webapp/app/entities/poll-participation/poll-participation.model';

describe('Component Tests', () => {

    describe('PollParticipation Management Detail Component', () => {
        let comp: PollParticipationDetailComponent;
        let fixture: ComponentFixture<PollParticipationDetailComponent>;
        let service: PollParticipationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [PollParticipationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PollParticipationService,
                    JhiEventManager
                ]
            }).overrideTemplate(PollParticipationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PollParticipationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PollParticipationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PollParticipation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pollParticipation).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
