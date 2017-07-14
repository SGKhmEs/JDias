import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ReshareComponent } from './reshare.component';
import { ReshareDetailComponent } from './reshare-detail.component';
import { ResharePopupComponent } from './reshare-dialog.component';
import { ReshareDeletePopupComponent } from './reshare-delete-dialog.component';

import { Principal } from '../../shared';

export const reshareRoute: Routes = [
    {
        path: 'reshare',
        component: ReshareComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.reshare.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reshare/:id',
        component: ReshareDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.reshare.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resharePopupRoute: Routes = [
    {
        path: 'reshare-new',
        component: ResharePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.reshare.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reshare/:id/edit',
        component: ResharePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.reshare.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reshare/:id/delete',
        component: ReshareDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.reshare.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
