import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    HashTagService,
    HashTagPopupService,
    HashTagComponent,
    HashTagDetailComponent,
    HashTagDialogComponent,
    HashTagPopupComponent,
    HashTagDeletePopupComponent,
    HashTagDeleteDialogComponent,
    hashTagRoute,
    hashTagPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hashTagRoute,
    ...hashTagPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HashTagComponent,
        HashTagDetailComponent,
        HashTagDialogComponent,
        HashTagDeleteDialogComponent,
        HashTagPopupComponent,
        HashTagDeletePopupComponent,
    ],
    entryComponents: [
        HashTagComponent,
        HashTagDialogComponent,
        HashTagPopupComponent,
        HashTagDeleteDialogComponent,
        HashTagDeletePopupComponent,
    ],
    providers: [
        HashTagService,
        HashTagPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasHashTagModule {}
