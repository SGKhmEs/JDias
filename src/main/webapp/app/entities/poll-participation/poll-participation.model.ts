import { Poll } from '../poll';
import { PollAnswer } from '../poll-answer';
export class PollParticipation {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentGuid?: string,
        public pollAnswerGuid?: string,
        public authorSignature?: string,
        public parentAuthorSignature?: string,
        public poll?: Poll,
        public pollAnswer?: PollAnswer,
    ) {
    }
}
