import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    EventParticipationService,
    EventParticipationPopupService,
    EventParticipationComponent,
    EventParticipationDetailComponent,
    EventParticipationDialogComponent,
    EventParticipationPopupComponent,
    EventParticipationDeletePopupComponent,
    EventParticipationDeleteDialogComponent,
    eventParticipationRoute,
    eventParticipationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...eventParticipationRoute,
    ...eventParticipationPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EventParticipationComponent,
        EventParticipationDetailComponent,
        EventParticipationDialogComponent,
        EventParticipationDeleteDialogComponent,
        EventParticipationPopupComponent,
        EventParticipationDeletePopupComponent,
    ],
    entryComponents: [
        EventParticipationComponent,
        EventParticipationDialogComponent,
        EventParticipationPopupComponent,
        EventParticipationDeleteDialogComponent,
        EventParticipationDeletePopupComponent,
    ],
    providers: [
        EventParticipationService,
        EventParticipationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasEventParticipationModule {}
