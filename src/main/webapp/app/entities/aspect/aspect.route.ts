import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AspectComponent } from './aspect.component';
import { AspectDetailComponent } from './aspect-detail.component';
import { AspectPopupComponent } from './aspect-dialog.component';
import { AspectDeletePopupComponent } from './aspect-delete-dialog.component';

import { Principal } from '../../shared';

export const aspectRoute: Routes = [
    {
        path: 'aspect',
        component: AspectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspect.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'aspect/:id',
        component: AspectDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspect.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aspectPopupRoute: Routes = [
    {
        path: 'aspect-new',
        component: AspectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspect.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aspect/:id/edit',
        component: AspectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspect.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aspect/:id/delete',
        component: AspectDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspect.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
