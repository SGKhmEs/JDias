import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RetractionComponent } from './retraction.component';
import { RetractionDetailComponent } from './retraction-detail.component';
import { RetractionPopupComponent } from './retraction-dialog.component';
import { RetractionDeletePopupComponent } from './retraction-delete-dialog.component';

export const retractionRoute: Routes = [
    {
        path: 'retraction',
        component: RetractionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.retraction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'retraction/:id',
        component: RetractionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.retraction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const retractionPopupRoute: Routes = [
    {
        path: 'retraction-new',
        component: RetractionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.retraction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'retraction/:id/edit',
        component: RetractionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.retraction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'retraction/:id/delete',
        component: RetractionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.retraction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
