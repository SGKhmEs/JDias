import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    TagFollowingService,
    TagFollowingPopupService,
    TagFollowingComponent,
    TagFollowingDetailComponent,
    TagFollowingDialogComponent,
    TagFollowingPopupComponent,
    TagFollowingDeletePopupComponent,
    TagFollowingDeleteDialogComponent,
    tagFollowingRoute,
    tagFollowingPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tagFollowingRoute,
    ...tagFollowingPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TagFollowingComponent,
        TagFollowingDetailComponent,
        TagFollowingDialogComponent,
        TagFollowingDeleteDialogComponent,
        TagFollowingPopupComponent,
        TagFollowingDeletePopupComponent,
    ],
    entryComponents: [
        TagFollowingComponent,
        TagFollowingDialogComponent,
        TagFollowingPopupComponent,
        TagFollowingDeleteDialogComponent,
        TagFollowingDeletePopupComponent,
    ],
    providers: [
        TagFollowingService,
        TagFollowingPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasTagFollowingModule {}
