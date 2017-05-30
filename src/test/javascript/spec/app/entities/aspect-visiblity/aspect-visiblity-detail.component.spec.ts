import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AspectVisiblityDetailComponent } from '../../../../../../main/webapp/app/entities/aspect-visiblity/aspect-visiblity-detail.component';
import { AspectVisiblityService } from '../../../../../../main/webapp/app/entities/aspect-visiblity/aspect-visiblity.service';
import { AspectVisiblity } from '../../../../../../main/webapp/app/entities/aspect-visiblity/aspect-visiblity.model';

describe('Component Tests', () => {

    describe('AspectVisiblity Management Detail Component', () => {
        let comp: AspectVisiblityDetailComponent;
        let fixture: ComponentFixture<AspectVisiblityDetailComponent>;
        let service: AspectVisiblityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [AspectVisiblityDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AspectVisiblityService,
                    EventManager
                ]
            }).overrideTemplate(AspectVisiblityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AspectVisiblityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AspectVisiblityService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AspectVisiblity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.aspectVisiblity).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
