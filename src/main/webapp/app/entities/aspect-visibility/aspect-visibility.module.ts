import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    AspectVisibilityService,
    AspectVisibilityPopupService,
    AspectVisibilityComponent,
    AspectVisibilityDetailComponent,
    AspectVisibilityDialogComponent,
    AspectVisibilityPopupComponent,
    AspectVisibilityDeletePopupComponent,
    AspectVisibilityDeleteDialogComponent,
    aspectVisibilityRoute,
    aspectVisibilityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...aspectVisibilityRoute,
    ...aspectVisibilityPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AspectVisibilityComponent,
        AspectVisibilityDetailComponent,
        AspectVisibilityDialogComponent,
        AspectVisibilityDeleteDialogComponent,
        AspectVisibilityPopupComponent,
        AspectVisibilityDeletePopupComponent,
    ],
    entryComponents: [
        AspectVisibilityComponent,
        AspectVisibilityDialogComponent,
        AspectVisibilityPopupComponent,
        AspectVisibilityDeleteDialogComponent,
        AspectVisibilityDeletePopupComponent,
    ],
    providers: [
        AspectVisibilityService,
        AspectVisibilityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasAspectVisibilityModule {}
