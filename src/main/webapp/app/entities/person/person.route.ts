import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PersonComponent } from './person.component';
import { PersonDetailComponent } from './person-detail.component';
import { PersonPopupComponent } from './person-dialog.component';
import { PersonDeletePopupComponent } from './person-delete-dialog.component';

import { Principal } from '../../shared';

export const personRoute: Routes = [
    {
        path: 'person',
        component: PersonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.person.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'person/:id',
        component: PersonDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.person.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personPopupRoute: Routes = [
    {
        path: 'person-new',
        component: PersonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.person.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person/:id/edit',
        component: PersonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.person.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person/:id/delete',
        component: PersonDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.person.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
