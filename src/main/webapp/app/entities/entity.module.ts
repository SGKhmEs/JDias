import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JDiasAccountDeletionModule } from './account-deletion/account-deletion.module';
import { JDiasAspectModule } from './aspect/aspect.module';
import { JDiasAspectVisiblityModule } from './aspect-visiblity/aspect-visiblity.module';
import { JDiasCommentModule } from './comment/comment.module';
import { JDiasContactModule } from './contact/contact.module';
import { JDiasConversationModule } from './conversation/conversation.module';
import { JDiasEventModule } from './event/event.module';
import { JDiasEventParticipationModule } from './event-participation/event-participation.module';
import { JDiasLikeModule } from './like/like.module';
import { JDiasLocationModule } from './location/location.module';
import { JDiasMessageModule } from './message/message.module';
import { JDiasParticipationModule } from './participation/participation.module';
import { JDiasPhotoModule } from './photo/photo.module';
import { JDiasPollModule } from './poll/poll.module';
import { JDiasPollAnswerModule } from './poll-answer/poll-answer.module';
import { JDiasPollParticipationModule } from './poll-participation/poll-participation.module';
import { JDiasPostModule } from './post/post.module';
import { JDiasProfileModule } from './profile/profile.module';
import { JDiasReshareModule } from './reshare/reshare.module';
import { JDiasRetractionModule } from './retraction/retraction.module';
import { JDiasStatusMessageModule } from './status-message/status-message.module';
import { JDiasTagModule } from './tag/tag.module';
import { JDiasHashTagModule } from './hash-tag/hash-tag.module';
import { JDiasUserAccountModule } from './user-account/user-account.module';
import { JDiasPersonModule } from './person/person.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JDiasAccountDeletionModule,
        JDiasAspectModule,
        JDiasAspectVisiblityModule,
        JDiasCommentModule,
        JDiasContactModule,
        JDiasConversationModule,
        JDiasEventModule,
        JDiasEventParticipationModule,
        JDiasLikeModule,
        JDiasLocationModule,
        JDiasMessageModule,
        JDiasParticipationModule,
        JDiasPhotoModule,
        JDiasPollModule,
        JDiasPollAnswerModule,
        JDiasPollParticipationModule,
        JDiasPostModule,
        JDiasProfileModule,
        JDiasReshareModule,
        JDiasRetractionModule,
        JDiasStatusMessageModule,
        JDiasTagModule,
        JDiasHashTagModule,
        JDiasUserAccountModule,
        JDiasPersonModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JDiasEntityModule {}
