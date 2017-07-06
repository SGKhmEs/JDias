import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AspectVisibilityComponent } from './aspect-visibility.component';
import { AspectVisibilityDetailComponent } from './aspect-visibility-detail.component';
import { AspectVisibilityPopupComponent } from './aspect-visibility-dialog.component';
import { AspectVisibilityDeletePopupComponent } from './aspect-visibility-delete-dialog.component';

import { Principal } from '../../shared';

export const aspectVisibilityRoute: Routes = [
    {
        path: 'aspect-visibility',
        component: AspectVisibilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectVisibility.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'aspect-visibility/:id',
        component: AspectVisibilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectVisibility.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aspectVisibilityPopupRoute: Routes = [
    {
        path: 'aspect-visibility-new',
        component: AspectVisibilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectVisibility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aspect-visibility/:id/edit',
        component: AspectVisibilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectVisibility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aspect-visibility/:id/delete',
        component: AspectVisibilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectVisibility.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
