import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { InteractionComponent } from './interaction.component';
import { InteractionDetailComponent } from './interaction-detail.component';
import { InteractionPopupComponent } from './interaction-dialog.component';
import { InteractionDeletePopupComponent } from './interaction-delete-dialog.component';

import { Principal } from '../../shared';


export const interactionRoute: Routes = [
  {
    path: 'interaction',
    component: InteractionComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jDiasApp.interaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'interaction/:id',
    component: InteractionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jDiasApp.interaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const interactionPopupRoute: Routes = [
  {
    path: 'interaction-new',
    component: InteractionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jDiasApp.interaction.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'interaction/:id/edit',
    component: InteractionPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jDiasApp.interaction.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'interaction/:id/delete',
    component: InteractionDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'jDiasApp.interaction.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
