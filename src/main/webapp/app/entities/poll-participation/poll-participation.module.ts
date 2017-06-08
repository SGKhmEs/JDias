import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    PollParticipationService,
    PollParticipationPopupService,
    PollParticipationComponent,
    PollParticipationDetailComponent,
    PollParticipationDialogComponent,
    PollParticipationPopupComponent,
    PollParticipationDeletePopupComponent,
    PollParticipationDeleteDialogComponent,
    pollParticipationRoute,
    pollParticipationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pollParticipationRoute,
    ...pollParticipationPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PollParticipationComponent,
        PollParticipationDetailComponent,
        PollParticipationDialogComponent,
        PollParticipationDeleteDialogComponent,
        PollParticipationPopupComponent,
        PollParticipationDeletePopupComponent,
    ],
    entryComponents: [
        PollParticipationComponent,
        PollParticipationDialogComponent,
        PollParticipationPopupComponent,
        PollParticipationDeleteDialogComponent,
        PollParticipationDeletePopupComponent,
    ],
    providers: [
        PollParticipationService,
        PollParticipationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasPollParticipationModule {}
