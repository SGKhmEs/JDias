import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    AccountDeletionService,
    AccountDeletionPopupService,
    AccountDeletionComponent,
    AccountDeletionDetailComponent,
    AccountDeletionDialogComponent,
    AccountDeletionPopupComponent,
    AccountDeletionDeletePopupComponent,
    AccountDeletionDeleteDialogComponent,
    accountDeletionRoute,
    accountDeletionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...accountDeletionRoute,
    ...accountDeletionPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AccountDeletionComponent,
        AccountDeletionDetailComponent,
        AccountDeletionDialogComponent,
        AccountDeletionDeleteDialogComponent,
        AccountDeletionPopupComponent,
        AccountDeletionDeletePopupComponent,
    ],
    entryComponents: [
        AccountDeletionComponent,
        AccountDeletionDialogComponent,
        AccountDeletionPopupComponent,
        AccountDeletionDeleteDialogComponent,
        AccountDeletionDeletePopupComponent,
    ],
    providers: [
        AccountDeletionService,
        AccountDeletionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasAccountDeletionModule {}
