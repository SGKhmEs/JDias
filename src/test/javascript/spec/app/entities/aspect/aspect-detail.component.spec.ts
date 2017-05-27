import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AspectDetailComponent } from '../../../../../../main/webapp/app/entities/aspect/aspect-detail.component';
import { AspectService } from '../../../../../../main/webapp/app/entities/aspect/aspect.service';
import { Aspect } from '../../../../../../main/webapp/app/entities/aspect/aspect.model';

describe('Component Tests', () => {

    describe('Aspect Management Detail Component', () => {
        let comp: AspectDetailComponent;
        let fixture: ComponentFixture<AspectDetailComponent>;
        let service: AspectService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [AspectDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AspectService,
                    EventManager
                ]
            }).overrideComponent(AspectDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AspectDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AspectService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Aspect(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.aspect).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
