import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RetractionDetailComponent } from '../../../../../../main/webapp/app/entities/retraction/retraction-detail.component';
import { RetractionService } from '../../../../../../main/webapp/app/entities/retraction/retraction.service';
import { Retraction } from '../../../../../../main/webapp/app/entities/retraction/retraction.model';

describe('Component Tests', () => {

    describe('Retraction Management Detail Component', () => {
        let comp: RetractionDetailComponent;
        let fixture: ComponentFixture<RetractionDetailComponent>;
        let service: RetractionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [RetractionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RetractionService,
                    EventManager
                ]
            }).overrideTemplate(RetractionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RetractionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RetractionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Retraction(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.retraction).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
