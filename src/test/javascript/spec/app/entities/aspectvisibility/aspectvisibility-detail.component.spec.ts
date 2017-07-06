import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AspectvisibilityDetailComponent } from '../../../../../../main/webapp/app/entities/aspectvisibility/aspectvisibility-detail.component';
import { AspectvisibilityService } from '../../../../../../main/webapp/app/entities/aspectvisibility/aspectvisibility.service';
import { Aspectvisibility } from '../../../../../../main/webapp/app/entities/aspectvisibility/aspectvisibility.model';

describe('Component Tests', () => {

    describe('Aspectvisibility Management Detail Component', () => {
        let comp: AspectvisibilityDetailComponent;
        let fixture: ComponentFixture<AspectvisibilityDetailComponent>;
        let service: AspectvisibilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [AspectvisibilityDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AspectvisibilityService,
                    EventManager
                ]
            }).overrideTemplate(AspectvisibilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AspectvisibilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AspectvisibilityService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Aspectvisibility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.aspectvisibility).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
