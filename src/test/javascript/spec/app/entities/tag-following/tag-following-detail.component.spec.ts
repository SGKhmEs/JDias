import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TagFollowingDetailComponent } from '../../../../../../main/webapp/app/entities/tag-following/tag-following-detail.component';
import { TagFollowingService } from '../../../../../../main/webapp/app/entities/tag-following/tag-following.service';
import { TagFollowing } from '../../../../../../main/webapp/app/entities/tag-following/tag-following.model';

describe('Component Tests', () => {

    describe('TagFollowing Management Detail Component', () => {
        let comp: TagFollowingDetailComponent;
        let fixture: ComponentFixture<TagFollowingDetailComponent>;
        let service: TagFollowingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [TagFollowingDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TagFollowingService,
                    JhiEventManager
                ]
            }).overrideTemplate(TagFollowingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TagFollowingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TagFollowingService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TagFollowing(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tagFollowing).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
