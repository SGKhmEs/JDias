import { PollAnswer } from '../poll-answer';
import { PollParticipation } from '../poll-participation';
export class Poll {
    constructor(
        public id?: number,
        public guid?: string,
        public question?: string,
        public pollanswers?: PollAnswer,
        public pollparticipants?: PollParticipation,
    ) {
    }
}
