import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    RetractionService,
    RetractionPopupService,
    RetractionComponent,
    RetractionDetailComponent,
    RetractionDialogComponent,
    RetractionPopupComponent,
    RetractionDeletePopupComponent,
    RetractionDeleteDialogComponent,
    retractionRoute,
    retractionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...retractionRoute,
    ...retractionPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RetractionComponent,
        RetractionDetailComponent,
        RetractionDialogComponent,
        RetractionDeleteDialogComponent,
        RetractionPopupComponent,
        RetractionDeletePopupComponent,
    ],
    entryComponents: [
        RetractionComponent,
        RetractionDialogComponent,
        RetractionPopupComponent,
        RetractionDeleteDialogComponent,
        RetractionDeletePopupComponent,
    ],
    providers: [
        RetractionService,
        RetractionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasRetractionModule {}
