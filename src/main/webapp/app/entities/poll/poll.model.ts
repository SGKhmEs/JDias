import { PollParticipation } from '../poll-participation';
/*export class Poll {
    constructor(
        public id?: number,
        public guid?: string,
        public question?: string,
        public pollanswers?: PollAnswer,
        public pollparticipants?: PollParticipation,
    ) {
    }
}*/

     export class Poll {
         constructor(
             public poll_id?: number,
             public post_id?: number,
             public question?: string,
             public poll_answers?: PollAnswer[],
             public participation_count?: number,
         ) {
         }
     }
     export class PollAnswer {
         constructor(
             public id?: number,
             public answer?: string,
             public poll_id?: number,
             public guid?: string,
         ) {
         }
     }
