import { Conversation } from '../conversation';
export class Message {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public conversationguid?: string,
        public text?: string,
        public createdat?: any,
        public conversation?: Conversation,
    ) {
    }
}
