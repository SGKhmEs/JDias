import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { StatusMessageDetailComponent } from '../../../../../../main/webapp/app/entities/status-message/status-message-detail.component';
import { StatusMessageService } from '../../../../../../main/webapp/app/entities/status-message/status-message.service';
import { StatusMessage } from '../../../../../../main/webapp/app/entities/status-message/status-message.model';

describe('Component Tests', () => {

    describe('StatusMessage Management Detail Component', () => {
        let comp: StatusMessageDetailComponent;
        let fixture: ComponentFixture<StatusMessageDetailComponent>;
        let service: StatusMessageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [StatusMessageDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    StatusMessageService,
                    JhiEventManager
                ]
            }).overrideTemplate(StatusMessageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StatusMessageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatusMessageService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new StatusMessage(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.statusMessage).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
