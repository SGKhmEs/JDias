import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AspectMembershipComponent } from './aspect-membership.component';
import { AspectMembershipDetailComponent } from './aspect-membership-detail.component';
import { AspectMembershipPopupComponent } from './aspect-membership-dialog.component';
import { AspectMembershipDeletePopupComponent } from './aspect-membership-delete-dialog.component';

import { Principal } from '../../shared';

export const aspectMembershipRoute: Routes = [
    {
        path: 'aspect-membership',
        component: AspectMembershipComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectMembership.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'aspect-membership/:id',
        component: AspectMembershipDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectMembership.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aspectMembershipPopupRoute: Routes = [
    {
        path: 'aspect-membership-new',
        component: AspectMembershipPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectMembership.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aspect-membership/:id/edit',
        component: AspectMembershipPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectMembership.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aspect-membership/:id/delete',
        component: AspectMembershipDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.aspectMembership.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
