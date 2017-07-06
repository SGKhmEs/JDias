import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConversationComponent } from './conversation.component';
import { ConversationDetailComponent } from './conversation-detail.component';
import { ConversationPopupComponent } from './conversation-dialog.component';
import { ConversationDeletePopupComponent } from './conversation-delete-dialog.component';

import { Principal } from '../../shared';

export const conversationRoute: Routes = [
    {
        path: 'conversation',
        component: ConversationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.conversation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'conversation/:id',
        component: ConversationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.conversation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const conversationPopupRoute: Routes = [
    {
        path: 'conversation-new',
        component: ConversationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.conversation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'conversation/:id/edit',
        component: ConversationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.conversation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'conversation/:id/delete',
        component: ConversationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.conversation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
