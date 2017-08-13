import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LikeComponent } from './like.component';
import { LikeDetailComponent } from './like-detail.component';
import { LikePopupComponent } from './like-dialog.component';
import { LikeDeletePopupComponent } from './like-delete-dialog.component';

export const likeRoute: Routes = [
    {
        path: 'like',
        component: LikeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.like.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'like/:id',
        component: LikeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.like.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const likePopupRoute: Routes = [
    {
        path: 'like-new',
        component: LikePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.like.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'like/:id/edit',
        component: LikePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.like.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'like/:id/delete',
        component: LikeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.like.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
