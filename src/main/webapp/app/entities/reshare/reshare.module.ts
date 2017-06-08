import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    ReshareService,
    ResharePopupService,
    ReshareComponent,
    ReshareDetailComponent,
    ReshareDialogComponent,
    ResharePopupComponent,
    ReshareDeletePopupComponent,
    ReshareDeleteDialogComponent,
    reshareRoute,
    resharePopupRoute,
} from './';

const ENTITY_STATES = [
    ...reshareRoute,
    ...resharePopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReshareComponent,
        ReshareDetailComponent,
        ReshareDialogComponent,
        ReshareDeleteDialogComponent,
        ResharePopupComponent,
        ReshareDeletePopupComponent,
    ],
    entryComponents: [
        ReshareComponent,
        ReshareDialogComponent,
        ResharePopupComponent,
        ReshareDeleteDialogComponent,
        ReshareDeletePopupComponent,
    ],
    providers: [
        ReshareService,
        ResharePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasReshareModule {}
