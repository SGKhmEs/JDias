import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { StatusMessageComponent } from './status-message.component';
import { StatusMessageDetailComponent } from './status-message-detail.component';
import { StatusMessagePopupComponent } from './status-message-dialog.component';
import { StatusMessageDeletePopupComponent } from './status-message-delete-dialog.component';

import { Principal } from '../../shared';

export const statusMessageRoute: Routes = [
    {
        path: 'status-message',
        component: StatusMessageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.statusMessage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'status-message/:id',
        component: StatusMessageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.statusMessage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'file',
        component: StatusMessageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.statusMessage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statusMessagePopupRoute: Routes = [
    {
        path: 'status-message-new',
        component: StatusMessagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.statusMessage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'status-message/:id/edit',
        component: StatusMessagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.statusMessage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'status-message/:id/delete',
        component: StatusMessageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.statusMessage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
