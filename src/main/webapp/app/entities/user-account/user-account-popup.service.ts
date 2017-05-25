import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { UserAccount } from './user-account.model';
import { UserAccountService } from './user-account.service';
@Injectable()
export class UserAccountPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private userAccountService: UserAccountService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.userAccountService.find(id).subscribe((userAccount) => {
                userAccount.rememberCreatedAt = this.datePipe
                    .transform(userAccount.rememberCreatedAt, 'yyyy-MM-ddThh:mm');
                userAccount.currentSignInAt = this.datePipe
                    .transform(userAccount.currentSignInAt, 'yyyy-MM-ddThh:mm');
                userAccount.lastSignInAt = this.datePipe
                    .transform(userAccount.lastSignInAt, 'yyyy-MM-ddThh:mm');
                userAccount.createdAt = this.datePipe
                    .transform(userAccount.createdAt, 'yyyy-MM-ddThh:mm');
                userAccount.updatedAt = this.datePipe
                    .transform(userAccount.updatedAt, 'yyyy-MM-ddThh:mm');
                userAccount.lockedAt = this.datePipe
                    .transform(userAccount.lockedAt, 'yyyy-MM-ddThh:mm');
                userAccount.lastSeen = this.datePipe
                    .transform(userAccount.lastSeen, 'yyyy-MM-ddThh:mm');
                userAccount.exportedAt = this.datePipe
                    .transform(userAccount.exportedAt, 'yyyy-MM-ddThh:mm');
                userAccount.exportedPhotosAt = this.datePipe
                    .transform(userAccount.exportedPhotosAt, 'yyyy-MM-ddThh:mm');
                this.userAccountModalRef(component, userAccount);
            });
        } else {
            return this.userAccountModalRef(component, new UserAccount());
        }
    }

    userAccountModalRef(component: Component, userAccount: UserAccount): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userAccount = userAccount;
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
