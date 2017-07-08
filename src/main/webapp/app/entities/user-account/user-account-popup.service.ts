import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UserAccount } from './user-account.model';
import { UserAccountService } from './user-account.service';

@Injectable()
export class UserAccountPopupService {
    private isOpen = false;
    constructor(
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
                if (userAccount.rememberCreatedAt) {
                    userAccount.rememberCreatedAt = {
                        year: userAccount.rememberCreatedAt.getFullYear(),
                        month: userAccount.rememberCreatedAt.getMonth() + 1,
                        day: userAccount.rememberCreatedAt.getDate()
                    };
                }
                if (userAccount.currentSignInAt) {
                    userAccount.currentSignInAt = {
                        year: userAccount.currentSignInAt.getFullYear(),
                        month: userAccount.currentSignInAt.getMonth() + 1,
                        day: userAccount.currentSignInAt.getDate()
                    };
                }
                if (userAccount.lastSignInAt) {
                    userAccount.lastSignInAt = {
                        year: userAccount.lastSignInAt.getFullYear(),
                        month: userAccount.lastSignInAt.getMonth() + 1,
                        day: userAccount.lastSignInAt.getDate()
                    };
                }
                if (userAccount.createdAt) {
                    userAccount.createdAt = {
                        year: userAccount.createdAt.getFullYear(),
                        month: userAccount.createdAt.getMonth() + 1,
                        day: userAccount.createdAt.getDate()
                    };
                }
                if (userAccount.updatedAt) {
                    userAccount.updatedAt = {
                        year: userAccount.updatedAt.getFullYear(),
                        month: userAccount.updatedAt.getMonth() + 1,
                        day: userAccount.updatedAt.getDate()
                    };
                }
                if (userAccount.lockedAt) {
                    userAccount.lockedAt = {
                        year: userAccount.lockedAt.getFullYear(),
                        month: userAccount.lockedAt.getMonth() + 1,
                        day: userAccount.lockedAt.getDate()
                    };
                }
                if (userAccount.lastSeen) {
                    userAccount.lastSeen = {
                        year: userAccount.lastSeen.getFullYear(),
                        month: userAccount.lastSeen.getMonth() + 1,
                        day: userAccount.lastSeen.getDate()
                    };
                }
                if (userAccount.exportedAt) {
                    userAccount.exportedAt = {
                        year: userAccount.exportedAt.getFullYear(),
                        month: userAccount.exportedAt.getMonth() + 1,
                        day: userAccount.exportedAt.getDate()
                    };
                }
                if (userAccount.exportedPhotosAt) {
                    userAccount.exportedPhotosAt = {
                        year: userAccount.exportedPhotosAt.getFullYear(),
                        month: userAccount.exportedPhotosAt.getMonth() + 1,
                        day: userAccount.exportedPhotosAt.getDate()
                    };
                }
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
