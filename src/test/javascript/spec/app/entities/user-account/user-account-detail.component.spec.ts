import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserAccountDetailComponent } from '../../../../../../main/webapp/app/entities/user-account/user-account-detail.component';
import { UserAccountService } from '../../../../../../main/webapp/app/entities/user-account/user-account.service';
import { UserAccount } from '../../../../../../main/webapp/app/entities/user-account/user-account.model';

describe('Component Tests', () => {

    describe('UserAccount Management Detail Component', () => {
        let comp: UserAccountDetailComponent;
        let fixture: ComponentFixture<UserAccountDetailComponent>;
        let service: UserAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [UserAccountDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserAccountService,
                    EventManager
                ]
            }).overrideTemplate(UserAccountDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAccountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAccountService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserAccount(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userAccount).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
