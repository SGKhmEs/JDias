import { Conversation } from '../conversation';
import { Person } from '../person';
export class Message {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public conversationGuid?: string,
        public text?: string,
        public createdAt?: any,
        public conversation?: Conversation,
        public person?: Person,
    ) {
    }
}
