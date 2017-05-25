import { Poll } from '../poll';
import { PollAnswer } from '../poll-answer';
export class PollParticipation {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentguid?: string,
        public pollanswerguid?: string,
        public authorsignature?: string,
        public parentauthorsignature?: string,
        public poll?: Poll,
        public pollAnswer?: PollAnswer,
    ) {
    }
}
