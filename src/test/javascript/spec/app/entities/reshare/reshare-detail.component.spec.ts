import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReshareDetailComponent } from '../../../../../../main/webapp/app/entities/reshare/reshare-detail.component';
import { ReshareService } from '../../../../../../main/webapp/app/entities/reshare/reshare.service';
import { Reshare } from '../../../../../../main/webapp/app/entities/reshare/reshare.model';

describe('Component Tests', () => {

    describe('Reshare Management Detail Component', () => {
        let comp: ReshareDetailComponent;
        let fixture: ComponentFixture<ReshareDetailComponent>;
        let service: ReshareService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [ReshareDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReshareService,
                    EventManager
                ]
            }).overrideComponent(ReshareDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReshareDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReshareService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Reshare(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.reshare).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
