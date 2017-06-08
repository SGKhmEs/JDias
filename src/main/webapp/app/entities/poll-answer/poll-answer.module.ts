import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    PollAnswerService,
    PollAnswerPopupService,
    PollAnswerComponent,
    PollAnswerDetailComponent,
    PollAnswerDialogComponent,
    PollAnswerPopupComponent,
    PollAnswerDeletePopupComponent,
    PollAnswerDeleteDialogComponent,
    pollAnswerRoute,
    pollAnswerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pollAnswerRoute,
    ...pollAnswerPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PollAnswerComponent,
        PollAnswerDetailComponent,
        PollAnswerDialogComponent,
        PollAnswerDeleteDialogComponent,
        PollAnswerPopupComponent,
        PollAnswerDeletePopupComponent,
    ],
    entryComponents: [
        PollAnswerComponent,
        PollAnswerDialogComponent,
        PollAnswerPopupComponent,
        PollAnswerDeleteDialogComponent,
        PollAnswerDeletePopupComponent,
    ],
    providers: [
        PollAnswerService,
        PollAnswerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasPollAnswerModule {}
