import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserAccountComponent } from './user-account.component';
import { UserAccountDetailComponent } from './user-account-detail.component';
import { UserAccountPopupComponent } from './user-account-dialog.component';
import { UserAccountDeletePopupComponent } from './user-account-delete-dialog.component';

import { Principal } from '../../shared';

export const userAccountRoute: Routes = [
    {
        path: 'user-account',
        component: UserAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.userAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-account/:id',
        component: UserAccountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.userAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userAccountPopupRoute: Routes = [
    {
        path: 'user-account-new',
        component: UserAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.userAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-account/:id/edit',
        component: UserAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.userAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-account/:id/delete',
        component: UserAccountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.userAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
