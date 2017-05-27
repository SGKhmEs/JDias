import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AspectVisiblityComponent } from './aspect-visiblity.component';
import { AspectVisiblityDetailComponent } from './aspect-visiblity-detail.component';
import { AspectVisiblityPopupComponent } from './aspect-visiblity-dialog.component';
import { AspectVisiblityDeletePopupComponent } from './aspect-visiblity-delete-dialog.component';

import { Principal } from '../../shared';

export const aspectVisiblityRoute: Routes = [
    {
        path: 'aspect-visiblity',
        component: AspectVisiblityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectVisiblity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'aspect-visiblity/:id',
        component: AspectVisiblityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectVisiblity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aspectVisiblityPopupRoute: Routes = [
    {
        path: 'aspect-visiblity-new',
        component: AspectVisiblityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectVisiblity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aspect-visiblity/:id/edit',
        component: AspectVisiblityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectVisiblity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aspect-visiblity/:id/delete',
        component: AspectVisiblityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectVisiblity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
