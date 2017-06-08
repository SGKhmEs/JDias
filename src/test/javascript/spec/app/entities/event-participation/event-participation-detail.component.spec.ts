import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EventParticipationDetailComponent } from '../../../../../../main/webapp/app/entities/event-participation/event-participation-detail.component';
import { EventParticipationService } from '../../../../../../main/webapp/app/entities/event-participation/event-participation.service';
import { EventParticipation } from '../../../../../../main/webapp/app/entities/event-participation/event-participation.model';

describe('Component Tests', () => {

    describe('EventParticipation Management Detail Component', () => {
        let comp: EventParticipationDetailComponent;
        let fixture: ComponentFixture<EventParticipationDetailComponent>;
        let service: EventParticipationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [EventParticipationDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EventParticipationService,
                    EventManager
                ]
            }).overrideTemplate(EventParticipationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EventParticipationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EventParticipationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EventParticipation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.eventParticipation).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
