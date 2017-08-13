import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { HashTagComponent } from './hash-tag.component';
import { HashTagDetailComponent } from './hash-tag-detail.component';
import { HashTagPopupComponent } from './hash-tag-dialog.component';
import { HashTagDeletePopupComponent } from './hash-tag-delete-dialog.component';

export const hashTagRoute: Routes = [
    {
        path: 'hash-tag',
        component: HashTagComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.hashTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hash-tag/:id',
        component: HashTagDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.hashTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hashTagPopupRoute: Routes = [
    {
        path: 'hash-tag-new',
        component: HashTagPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.hashTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hash-tag/:id/edit',
        component: HashTagPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.hashTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hash-tag/:id/delete',
        component: HashTagDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.hashTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
