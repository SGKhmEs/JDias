import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AccountDeletionComponent } from './account-deletion.component';
import { AccountDeletionDetailComponent } from './account-deletion-detail.component';
import { AccountDeletionPopupComponent } from './account-deletion-dialog.component';
import { AccountDeletionDeletePopupComponent } from './account-deletion-delete-dialog.component';

import { Principal } from '../../shared';

export const accountDeletionRoute: Routes = [
    {
        path: 'account-deletion',
        component: AccountDeletionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.accountDeletion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'account-deletion/:id',
        component: AccountDeletionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.accountDeletion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const accountDeletionPopupRoute: Routes = [
    {
        path: 'account-deletion-new',
        component: AccountDeletionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.accountDeletion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'account-deletion/:id/edit',
        component: AccountDeletionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.accountDeletion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'account-deletion/:id/delete',
        component: AccountDeletionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.accountDeletion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
