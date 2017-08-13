import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PollComponent } from './poll.component';
import { PollDetailComponent } from './poll-detail.component';
import { PollPopupComponent } from './poll-dialog.component';
import { PollDeletePopupComponent } from './poll-delete-dialog.component';

export const pollRoute: Routes = [
    {
        path: 'poll',
        component: PollComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.poll.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'poll/:id',
        component: PollDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.poll.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pollPopupRoute: Routes = [
    {
        path: 'poll-new',
        component: PollPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.poll.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poll/:id/edit',
        component: PollPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.poll.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poll/:id/delete',
        component: PollDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.poll.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
