import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    TaggingService,
    TaggingPopupService,
    TaggingComponent,
    TaggingDetailComponent,
    TaggingDialogComponent,
    TaggingPopupComponent,
    TaggingDeletePopupComponent,
    TaggingDeleteDialogComponent,
    taggingRoute,
    taggingPopupRoute,
} from './';

const ENTITY_STATES = [
    ...taggingRoute,
    ...taggingPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TaggingComponent,
        TaggingDetailComponent,
        TaggingDialogComponent,
        TaggingDeleteDialogComponent,
        TaggingPopupComponent,
        TaggingDeletePopupComponent,
    ],
    entryComponents: [
        TaggingComponent,
        TaggingDialogComponent,
        TaggingPopupComponent,
        TaggingDeleteDialogComponent,
        TaggingDeletePopupComponent,
    ],
    providers: [
        TaggingService,
        TaggingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasTaggingModule {}
