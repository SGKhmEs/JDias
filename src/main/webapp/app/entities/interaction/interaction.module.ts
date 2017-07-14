import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';

import {
    InteractionService,
    InteractionPopupService,
    InteractionComponent,
    InteractionDetailComponent,
    InteractionDialogComponent,
    InteractionPopupComponent,
    InteractionDeletePopupComponent,
    InteractionDeleteDialogComponent,
    interactionRoute,
    interactionPopupRoute,
} from './';

let ENTITY_STATES = [
    ...interactionRoute,
    ...interactionPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InteractionComponent,
        InteractionDetailComponent,
        InteractionDialogComponent,
        InteractionDeleteDialogComponent,
        InteractionPopupComponent,
        InteractionDeletePopupComponent,
    ],
    entryComponents: [
        InteractionComponent,
        InteractionDialogComponent,
        InteractionPopupComponent,
        InteractionDeleteDialogComponent,
        InteractionDeletePopupComponent,
    ],
    providers: [
        InteractionService,
        InteractionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasInteractionModule {}
