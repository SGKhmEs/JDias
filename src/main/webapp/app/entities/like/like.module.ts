import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    LikeService,
    LikePopupService,
    LikeComponent,
    LikeDetailComponent,
    LikeDialogComponent,
    LikePopupComponent,
    LikeDeletePopupComponent,
    LikeDeleteDialogComponent,
    likeRoute,
    likePopupRoute,
} from './';

const ENTITY_STATES = [
    ...likeRoute,
    ...likePopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LikeComponent,
        LikeDetailComponent,
        LikeDialogComponent,
        LikeDeleteDialogComponent,
        LikePopupComponent,
        LikeDeletePopupComponent,
    ],
    entryComponents: [
        LikeComponent,
        LikeDialogComponent,
        LikePopupComponent,
        LikeDeleteDialogComponent,
        LikeDeletePopupComponent,
    ],
    providers: [
        LikeService,
        LikePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasLikeModule {}
