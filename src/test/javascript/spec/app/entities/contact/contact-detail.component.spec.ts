import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { JDiasTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ContactDetailComponent } from '../../../../../../main/webapp/app/entities/contact/contact-detail.component';
import { ContactService } from '../../../../../../main/webapp/app/entities/contact/contact.service';
import { Contact } from '../../../../../../main/webapp/app/entities/contact/contact.model';

describe('Component Tests', () => {

    describe('Contact Management Detail Component', () => {
        let comp: ContactDetailComponent;
        let fixture: ComponentFixture<ContactDetailComponent>;
        let service: ContactService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JDiasTestModule],
                declarations: [ContactDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ContactService,
                    JhiEventManager
                ]
            }).overrideTemplate(ContactDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContactDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Contact(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.contact).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
