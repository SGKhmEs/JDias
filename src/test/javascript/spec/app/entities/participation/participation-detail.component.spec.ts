/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ParticipationDetailComponent } from '../../../../../../main/webapp/app/entities/participation/participation-detail.component';
import { ParticipationService } from '../../../../../../main/webapp/app/entities/participation/participation.service';
import { Participation } from '../../../../../../main/webapp/app/entities/participation/participation.model';

describe('Component Tests', () => {

    describe('Participation Management Detail Component', () => {
        let comp: ParticipationDetailComponent;
        let fixture: ComponentFixture<ParticipationDetailComponent>;
        let service: ParticipationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [ParticipationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ParticipationService,
                    JhiEventManager
                ]
            }).overrideTemplate(ParticipationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParticipationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParticipationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Participation(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.participation).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
