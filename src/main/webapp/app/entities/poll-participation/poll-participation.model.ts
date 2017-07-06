import { BaseEntity } from './../../shared';

export class PollParticipation implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentGuid?: string,
        public pollAnswerGuid?: string,
        public authorSignature?: string,
        public parentAuthorSignature?: string,
        public poll?: BaseEntity,
        public pollAnswer?: BaseEntity,
    ) {
    }
}
