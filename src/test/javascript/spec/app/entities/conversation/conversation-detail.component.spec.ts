import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConversationDetailComponent } from '../../../../../../main/webapp/app/entities/conversation/conversation-detail.component';
import { ConversationService } from '../../../../../../main/webapp/app/entities/conversation/conversation.service';
import { Conversation } from '../../../../../../main/webapp/app/entities/conversation/conversation.model';

describe('Component Tests', () => {

    describe('Conversation Management Detail Component', () => {
        let comp: ConversationDetailComponent;
        let fixture: ComponentFixture<ConversationDetailComponent>;
        let service: ConversationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [ConversationDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConversationService,
                    EventManager
                ]
            }).overrideTemplate(ConversationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConversationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConversationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Conversation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.conversation).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
