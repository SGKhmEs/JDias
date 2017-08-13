import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PollParticipationComponent } from './poll-participation.component';
import { PollParticipationDetailComponent } from './poll-participation-detail.component';
import { PollParticipationPopupComponent } from './poll-participation-dialog.component';
import { PollParticipationDeletePopupComponent } from './poll-participation-delete-dialog.component';

export const pollParticipationRoute: Routes = [
    {
        path: 'poll-participation',
        component: PollParticipationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.pollParticipation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'poll-participation/:id',
        component: PollParticipationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.pollParticipation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pollParticipationPopupRoute: Routes = [
    {
        path: 'poll-participation-new',
        component: PollParticipationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.pollParticipation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poll-participation/:id/edit',
        component: PollParticipationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.pollParticipation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poll-participation/:id/delete',
        component: PollParticipationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.pollParticipation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
