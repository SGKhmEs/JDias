import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PollAnswerComponent } from './poll-answer.component';
import { PollAnswerDetailComponent } from './poll-answer-detail.component';
import { PollAnswerPopupComponent } from './poll-answer-dialog.component';
import { PollAnswerDeletePopupComponent } from './poll-answer-delete-dialog.component';

import { Principal } from '../../shared';

export const pollAnswerRoute: Routes = [
    {
        path: 'poll-answer',
        component: PollAnswerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.pollAnswer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'poll-answer/:id',
        component: PollAnswerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.pollAnswer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pollAnswerPopupRoute: Routes = [
    {
        path: 'poll-answer-new',
        component: PollAnswerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.pollAnswer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poll-answer/:id/edit',
        component: PollAnswerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.pollAnswer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poll-answer/:id/delete',
        component: PollAnswerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jDiasApp.pollAnswer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
