import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AccountDeletionDetailComponent } from '../../../../../../main/webapp/app/entities/account-deletion/account-deletion-detail.component';
import { AccountDeletionService } from '../../../../../../main/webapp/app/entities/account-deletion/account-deletion.service';
import { AccountDeletion } from '../../../../../../main/webapp/app/entities/account-deletion/account-deletion.model';

describe('Component Tests', () => {

    describe('AccountDeletion Management Detail Component', () => {
        let comp: AccountDeletionDetailComponent;
        let fixture: ComponentFixture<AccountDeletionDetailComponent>;
        let service: AccountDeletionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [AccountDeletionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AccountDeletionService,
                    JhiEventManager
                ]
            }).overrideTemplate(AccountDeletionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AccountDeletionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountDeletionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AccountDeletion(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.accountDeletion).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
