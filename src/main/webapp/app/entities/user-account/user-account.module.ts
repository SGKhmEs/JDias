import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import { JDiasAdminModule } from '../../admin/admin.module';
import {
    UserAccountService,
    UserAccountPopupService,
    UserAccountComponent,
    UserAccountDetailComponent,
    UserAccountDialogComponent,
    UserAccountPopupComponent,
    UserAccountDeletePopupComponent,
    UserAccountDeleteDialogComponent,
    userAccountRoute,
    userAccountPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userAccountRoute,
    ...userAccountPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        JDiasAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserAccountComponent,
        UserAccountDetailComponent,
        UserAccountDialogComponent,
        UserAccountDeleteDialogComponent,
        UserAccountPopupComponent,
        UserAccountDeletePopupComponent,
    ],
    entryComponents: [
        UserAccountComponent,
        UserAccountDialogComponent,
        UserAccountPopupComponent,
        UserAccountDeleteDialogComponent,
        UserAccountDeletePopupComponent,
    ],
    providers: [
        UserAccountService,
        UserAccountPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasUserAccountModule {}
