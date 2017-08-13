import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Profile } from './profile.model';
import { ProfileService } from './profile.service';

@Injectable()
export class ProfilePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private profileService: ProfileService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.profileService.find(id).subscribe((profile) => {
                    if (profile.birthday) {
                        profile.birthday = {
                            year: profile.birthday.getFullYear(),
                            month: profile.birthday.getMonth() + 1,
                            day: profile.birthday.getDate()
                        };
                    }
                    this.ngbModalRef = this.profileModalRef(component, profile);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.profileModalRef(component, new Profile());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    profileModalRef(component: Component, profile: Profile): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.profile = profile;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
