import { Message } from '../message';
import { Person } from '../person';
export class Conversation {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public subject?: string,
        public createdAt?: any,
        public message?: string,
        public updatedAt?: any,
        public messages?: Message,
        public participants?: Person,
    ) {
    }
}
