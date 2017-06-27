import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TagFollowingComponent } from './tag-following.component';
import { TagFollowingDetailComponent } from './tag-following-detail.component';
import { TagFollowingPopupComponent } from './tag-following-dialog.component';
import { TagFollowingDeletePopupComponent } from './tag-following-delete-dialog.component';

import { Principal } from '../../shared';

export const tagFollowingRoute: Routes = [
    {
        path: 'tag-following',
        component: TagFollowingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.tagFollowing.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tag-following/:id',
        component: TagFollowingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.tagFollowing.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tagFollowingPopupRoute: Routes = [
    {
        path: 'tag-following-new',
        component: TagFollowingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.tagFollowing.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tag-following/:id/edit',
        component: TagFollowingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.tagFollowing.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tag-following/:id/delete',
        component: TagFollowingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.tagFollowing.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
