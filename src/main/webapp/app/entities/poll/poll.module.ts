import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    PollService,
    PollPopupService,
    PollComponent,
    PollDetailComponent,
    PollDialogComponent,
    PollPopupComponent,
    PollDeletePopupComponent,
    PollDeleteDialogComponent,
    pollRoute,
    pollPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pollRoute,
    ...pollPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PollComponent,
        PollDetailComponent,
        PollDialogComponent,
        PollDeleteDialogComponent,
        PollPopupComponent,
        PollDeletePopupComponent,
    ],
    entryComponents: [
        PollComponent,
        PollDialogComponent,
        PollPopupComponent,
        PollDeleteDialogComponent,
        PollDeletePopupComponent,
    ],
    providers: [
        PollService,
        PollPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasPollModule {}
