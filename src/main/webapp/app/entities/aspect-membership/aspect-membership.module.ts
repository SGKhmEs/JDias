import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JDiasSharedModule } from '../../shared';
import {
    AspectMembershipService,
    AspectMembershipPopupService,
    AspectMembershipComponent,
    AspectMembershipDetailComponent,
    AspectMembershipDialogComponent,
    AspectMembershipPopupComponent,
    AspectMembershipDeletePopupComponent,
    AspectMembershipDeleteDialogComponent,
    aspectMembershipRoute,
    aspectMembershipPopupRoute,
} from './';

const ENTITY_STATES = [
    ...aspectMembershipRoute,
    ...aspectMembershipPopupRoute,
];

@NgModule({
    imports: [
        JDiasSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AspectMembershipComponent,
        AspectMembershipDetailComponent,
        AspectMembershipDialogComponent,
        AspectMembershipDeleteDialogComponent,
        AspectMembershipPopupComponent,
        AspectMembershipDeletePopupComponent,
    ],
    entryComponents: [
        AspectMembershipComponent,
        AspectMembershipDialogComponent,
        AspectMembershipPopupComponent,
        AspectMembershipDeleteDialogComponent,
        AspectMembershipDeletePopupComponent,
    ],
    providers: [
        AspectMembershipService,
        AspectMembershipPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasAspectMembershipModule {}
