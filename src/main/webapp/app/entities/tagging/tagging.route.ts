import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TaggingComponent } from './tagging.component';
import { TaggingDetailComponent } from './tagging-detail.component';
import { TaggingPopupComponent } from './tagging-dialog.component';
import { TaggingDeletePopupComponent } from './tagging-delete-dialog.component';

import { Principal } from '../../shared';

export const taggingRoute: Routes = [
    {
        path: 'tagging',
        component: TaggingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.tagging.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tagging/:id',
        component: TaggingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.tagging.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const taggingPopupRoute: Routes = [
    {
        path: 'tagging-new',
        component: TaggingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.tagging.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tagging/:id/edit',
        component: TaggingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.tagging.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tagging/:id/delete',
        component: TaggingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.tagging.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
