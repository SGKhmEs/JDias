import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PhotoDetailComponent } from '../../../../../../main/webapp/app/entities/photo/photo-detail.component';
import { PhotoService } from '../../../../../../main/webapp/app/entities/photo/photo.service';
import { Photo } from '../../../../../../main/webapp/app/entities/photo/photo.model';

describe('Component Tests', () => {

    describe('Photo Management Detail Component', () => {
        let comp: PhotoDetailComponent;
        let fixture: ComponentFixture<PhotoDetailComponent>;
        let service: PhotoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [PhotoDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PhotoService,
                    EventManager
                ]
            }).overrideTemplate(PhotoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PhotoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PhotoService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Photo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.photo).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
