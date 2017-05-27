import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    AspectService,
    AspectPopupService,
    AspectComponent,
    AspectDetailComponent,
    AspectDialogComponent,
    AspectPopupComponent,
    AspectDeletePopupComponent,
    AspectDeleteDialogComponent,
    aspectRoute,
    aspectPopupRoute,
} from './';

const ENTITY_STATES = [
    ...aspectRoute,
    ...aspectPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AspectComponent,
        AspectDetailComponent,
        AspectDialogComponent,
        AspectDeleteDialogComponent,
        AspectPopupComponent,
        AspectDeletePopupComponent,
    ],
    entryComponents: [
        AspectComponent,
        AspectDialogComponent,
        AspectPopupComponent,
        AspectDeleteDialogComponent,
        AspectDeletePopupComponent,
    ],
    providers: [
        AspectService,
        AspectPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasAspectModule {}
