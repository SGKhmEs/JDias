import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AccountDeletion } from './account-deletion.model';
import { AccountDeletionService } from './account-deletion.service';

@Injectable()
export class AccountDeletionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private accountDeletionService: AccountDeletionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.accountDeletionService.find(id).subscribe((accountDeletion) => {
                this.accountDeletionModalRef(component, accountDeletion);
            });
        } else {
            return this.accountDeletionModalRef(component, new AccountDeletion());
        }
    }

    accountDeletionModalRef(component: Component, accountDeletion: AccountDeletion): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.accountDeletion = accountDeletion;
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
