import { Poll } from '../poll';
import { PollParticipation } from '../poll-participation';
export class PollAnswer {
    constructor(
        public id?: number,
        public guid?: string,
        public answer?: string,
        public poll?: Poll,
        public pollanswers1?: PollParticipation,
    ) {
    }
}
