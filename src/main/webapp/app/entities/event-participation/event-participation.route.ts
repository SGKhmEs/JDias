import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EventParticipationComponent } from './event-participation.component';
import { EventParticipationDetailComponent } from './event-participation-detail.component';
import { EventParticipationPopupComponent } from './event-participation-dialog.component';
import { EventParticipationDeletePopupComponent } from './event-participation-delete-dialog.component';

export const eventParticipationRoute: Routes = [
    {
        path: 'event-participation',
        component: EventParticipationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.eventParticipation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'event-participation/:id',
        component: EventParticipationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.eventParticipation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eventParticipationPopupRoute: Routes = [
    {
        path: 'event-participation-new',
        component: EventParticipationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.eventParticipation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'event-participation/:id/edit',
        component: EventParticipationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.eventParticipation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'event-participation/:id/delete',
        component: EventParticipationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.eventParticipation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
