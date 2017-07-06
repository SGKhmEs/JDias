import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AspectVisibilityDetailComponent } from '../../../../../../main/webapp/app/entities/aspect-visibility/aspect-visibility-detail.component';
import { AspectVisibilityService } from '../../../../../../main/webapp/app/entities/aspect-visibility/aspect-visibility.service';
import { AspectVisibility } from '../../../../../../main/webapp/app/entities/aspect-visibility/aspect-visibility.model';

describe('Component Tests', () => {

    describe('AspectVisibility Management Detail Component', () => {
        let comp: AspectVisibilityDetailComponent;
        let fixture: ComponentFixture<AspectVisibilityDetailComponent>;
        let service: AspectVisibilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [AspectVisibilityDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AspectVisibilityService,
                    EventManager
                ]
            }).overrideTemplate(AspectVisibilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AspectVisibilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AspectVisibilityService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AspectVisibility(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.aspectVisibility).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
