import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AspectMembershipDetailComponent } from '../../../../../../main/webapp/app/entities/aspect-membership/aspect-membership-detail.component';
import { AspectMembershipService } from '../../../../../../main/webapp/app/entities/aspect-membership/aspect-membership.service';
import { AspectMembership } from '../../../../../../main/webapp/app/entities/aspect-membership/aspect-membership.model';

describe('Component Tests', () => {

    describe('AspectMembership Management Detail Component', () => {
        let comp: AspectMembershipDetailComponent;
        let fixture: ComponentFixture<AspectMembershipDetailComponent>;
        let service: AspectMembershipService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [AspectMembershipDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AspectMembershipService,
                    EventManager
                ]
            }).overrideComponent(AspectMembershipDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AspectMembershipDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AspectMembershipService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AspectMembership(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.aspectMembership).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
