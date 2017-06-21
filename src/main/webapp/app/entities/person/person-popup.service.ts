import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Person } from './person.model';
import { PersonService } from './person.service';

@Injectable()
export class PersonPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personService: PersonService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.personService.find(id).subscribe((person) => {
                if (person.createdAt) {
                    person.createdAt = {
                        year: person.createdAt.getFullYear(),
                        month: person.createdAt.getMonth() + 1,
                        day: person.createdAt.getDate()
                    };
                }
                if (person.updatedAt) {
                    person.updatedAt = {
                        year: person.updatedAt.getFullYear(),
                        month: person.updatedAt.getMonth() + 1,
                        day: person.updatedAt.getDate()
                    };
                }
                this.personModalRef(component, person);
            });
        } else {
            return this.personModalRef(component, new Person());
        }
    }

    personModalRef(component: Component, person: Person): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.person = person;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
