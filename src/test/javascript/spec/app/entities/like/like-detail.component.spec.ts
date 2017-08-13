/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LikeDetailComponent } from '../../../../../../main/webapp/app/entities/like/like-detail.component';
import { LikeService } from '../../../../../../main/webapp/app/entities/like/like.service';
import { Like } from '../../../../../../main/webapp/app/entities/like/like.model';

describe('Component Tests', () => {

    describe('Like Management Detail Component', () => {
        let comp: LikeDetailComponent;
        let fixture: ComponentFixture<LikeDetailComponent>;
        let service: LikeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [LikeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LikeService,
                    JhiEventManager
                ]
            }).overrideTemplate(LikeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LikeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LikeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Like(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.like).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
