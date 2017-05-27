import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TaggingDetailComponent } from '../../../../../../main/webapp/app/entities/tagging/tagging-detail.component';
import { TaggingService } from '../../../../../../main/webapp/app/entities/tagging/tagging.service';
import { Tagging } from '../../../../../../main/webapp/app/entities/tagging/tagging.model';

describe('Component Tests', () => {

    describe('Tagging Management Detail Component', () => {
        let comp: TaggingDetailComponent;
        let fixture: ComponentFixture<TaggingDetailComponent>;
        let service: TaggingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [TaggingDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TaggingService,
                    EventManager
                ]
            }).overrideComponent(TaggingDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaggingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaggingService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Tagging(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tagging).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
