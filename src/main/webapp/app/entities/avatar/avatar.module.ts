import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';

import {
    AvatarService,
    AvatarPopupService,
    AvatarComponent,
    AvatarDetailComponent,
    AvatarDialogComponent,
    AvatarPopupComponent,
    AvatarDeletePopupComponent,
    AvatarDeleteDialogComponent,
    avatarRoute,
    avatarPopupRoute,
} from './';

let ENTITY_STATES = [
    ...avatarRoute,
    ...avatarPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AvatarComponent,
        AvatarDetailComponent,
        AvatarDialogComponent,
        AvatarDeleteDialogComponent,
        AvatarPopupComponent,
        AvatarDeletePopupComponent,
    ],
    entryComponents: [
        AvatarComponent,
        AvatarDialogComponent,
        AvatarPopupComponent,
        AvatarDeleteDialogComponent,
        AvatarDeletePopupComponent,
    ],
    providers: [
        AvatarService,
        AvatarPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasAvatarModule {}
