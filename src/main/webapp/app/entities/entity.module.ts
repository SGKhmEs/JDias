import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JDiasAspectVisibilityModule } from './aspect-visibility/aspect-visibility.module';
import { JDiasAspectvisibilityModule } from './aspectvisibility/aspectvisibility.module';
import { JDiasAspectModule } from './aspect/aspect.module';
import { JDiasPersonModule } from './person/person.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JDiasAspectVisibilityModule,
        JDiasAspectvisibilityModule,
        JDiasAspectModule,
        JDiasPersonModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasEntityModule {}
