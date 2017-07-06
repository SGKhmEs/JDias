import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    AspectvisibilityService,
    AspectvisibilityPopupService,
    AspectvisibilityComponent,
    AspectvisibilityDetailComponent,
    AspectvisibilityDialogComponent,
    AspectvisibilityPopupComponent,
    AspectvisibilityDeletePopupComponent,
    AspectvisibilityDeleteDialogComponent,
    aspectvisibilityRoute,
    aspectvisibilityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...aspectvisibilityRoute,
    ...aspectvisibilityPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AspectvisibilityComponent,
        AspectvisibilityDetailComponent,
        AspectvisibilityDialogComponent,
        AspectvisibilityDeleteDialogComponent,
        AspectvisibilityPopupComponent,
        AspectvisibilityDeletePopupComponent,
    ],
    entryComponents: [
        AspectvisibilityComponent,
        AspectvisibilityDialogComponent,
        AspectvisibilityPopupComponent,
        AspectvisibilityDeleteDialogComponent,
        AspectvisibilityDeletePopupComponent,
    ],
    providers: [
        AspectvisibilityService,
        AspectvisibilityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasAspectvisibilityModule {}
