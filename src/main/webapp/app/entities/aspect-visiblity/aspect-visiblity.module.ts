import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    AspectVisiblityService,
    AspectVisiblityPopupService,
    AspectVisiblityComponent,
    AspectVisiblityDetailComponent,
    AspectVisiblityDialogComponent,
    AspectVisiblityPopupComponent,
    AspectVisiblityDeletePopupComponent,
    AspectVisiblityDeleteDialogComponent,
    aspectVisiblityRoute,
    aspectVisiblityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...aspectVisiblityRoute,
    ...aspectVisiblityPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AspectVisiblityComponent,
        AspectVisiblityDetailComponent,
        AspectVisiblityDialogComponent,
        AspectVisiblityDeleteDialogComponent,
        AspectVisiblityPopupComponent,
        AspectVisiblityDeletePopupComponent,
    ],
    entryComponents: [
        AspectVisiblityComponent,
        AspectVisiblityDialogComponent,
        AspectVisiblityPopupComponent,
        AspectVisiblityDeleteDialogComponent,
        AspectVisiblityDeletePopupComponent,
    ],
    providers: [
        AspectVisiblityService,
        AspectVisiblityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasAspectVisiblityModule {}
