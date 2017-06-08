import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PollDetailComponent } from '../../../../../../main/webapp/app/entities/poll/poll-detail.component';
import { PollService } from '../../../../../../main/webapp/app/entities/poll/poll.service';
import { Poll } from '../../../../../../main/webapp/app/entities/poll/poll.model';

describe('Component Tests', () => {

    describe('Poll Management Detail Component', () => {
        let comp: PollDetailComponent;
        let fixture: ComponentFixture<PollDetailComponent>;
        let service: PollService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [PollDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PollService,
                    EventManager
                ]
            }).overrideTemplate(PollDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PollDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PollService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Poll(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.poll).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
