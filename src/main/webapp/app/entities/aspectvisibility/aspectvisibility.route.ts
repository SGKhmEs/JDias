import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AspectvisibilityComponent } from './aspectvisibility.component';
import { AspectvisibilityDetailComponent } from './aspectvisibility-detail.component';
import { AspectvisibilityPopupComponent } from './aspectvisibility-dialog.component';
import { AspectvisibilityDeletePopupComponent } from './aspectvisibility-delete-dialog.component';

import { Principal } from '../../shared';

export const aspectvisibilityRoute: Routes = [
    {
        path: 'aspectvisibility',
        component: AspectvisibilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectvisibility.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'aspectvisibility/:id',
        component: AspectvisibilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectvisibility.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aspectvisibilityPopupRoute: Routes = [
    {
        path: 'aspectvisibility-new',
        component: AspectvisibilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectvisibility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aspectvisibility/:id/edit',
        component: AspectvisibilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectvisibility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aspectvisibility/:id/delete',
        component: AspectvisibilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectvisibility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
