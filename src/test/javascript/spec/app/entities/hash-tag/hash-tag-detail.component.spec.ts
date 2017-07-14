import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HashTagDetailComponent } from '../../../../../../main/webapp/app/entities/hash-tag/hash-tag-detail.component';
import { HashTagService } from '../../../../../../main/webapp/app/entities/hash-tag/hash-tag.service';
import { HashTag } from '../../../../../../main/webapp/app/entities/hash-tag/hash-tag.model';

describe('Component Tests', () => {

    describe('HashTag Management Detail Component', () => {
        let comp: HashTagDetailComponent;
        let fixture: ComponentFixture<HashTagDetailComponent>;
        let service: HashTagService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [HashTagDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HashTagService,
                    EventManager
                ]
            }).overrideTemplate(HashTagDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HashTagDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HashTagService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HashTag(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.hashTag).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
