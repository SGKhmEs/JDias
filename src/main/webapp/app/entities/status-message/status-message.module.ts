import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    StatusMessageService,
    StatusMessagePopupService,
    StatusMessageComponent,
    StatusMessageDetailComponent,
    StatusMessageDialogComponent,
    StatusMessagePopupComponent,
    StatusMessageDeletePopupComponent,
    StatusMessageDeleteDialogComponent,
    statusMessageRoute,
    statusMessagePopupRoute,
} from './';

const ENTITY_STATES = [
    ...statusMessageRoute,
    ...statusMessagePopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        StatusMessageComponent,
        StatusMessageDetailComponent,
        StatusMessageDialogComponent,
        StatusMessageDeleteDialogComponent,
        StatusMessagePopupComponent,
        StatusMessageDeletePopupComponent,
    ],
    entryComponents: [
        StatusMessageComponent,
        StatusMessageDialogComponent,
        StatusMessagePopupComponent,
        StatusMessageDeleteDialogComponent,
        StatusMessageDeletePopupComponent,
    ],
    providers: [
        StatusMessageService,
        StatusMessagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasStatusMessageModule {}
