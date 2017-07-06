import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JDiasAspectvisibilityModule } from './aspectVisibility/aspectVisibility.module';
import { JDiasAspectModule } from './aspect/aspect.module';
import { JDiasPersonModule } from './person/person.module';
import { JDiasPostModule } from './post/post.module';
import { JDiasAspectVisibilityModule } from './aspect-visibility/aspect-visibility.module';
import { JDiasContactModule } from './contact/contact.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JDiasAspectVisibilityModule,
        JDiasAspectModule,
        JDiasPersonModule,
        JDiasPostModule,
        JDiasContactModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasEntityModule {}
