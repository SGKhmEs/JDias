import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PhotoComponent } from './photo.component';
import { PhotoDetailComponent } from './photo-detail.component';
import { PhotoPopupComponent } from './photo-dialog.component';
import { PhotoDeletePopupComponent } from './photo-delete-dialog.component';

import { Principal } from '../../shared';

export const photoRoute: Routes = [
    {
        path: 'photo',
        component: PhotoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.photo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'photo/:id',
        component: PhotoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.photo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const photoPopupRoute: Routes = [
    {
        path: 'photo-new',
        component: PhotoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.photo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'photo/:id/edit',
        component: PhotoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.photo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'photo/:id/delete',
        component: PhotoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.photo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
