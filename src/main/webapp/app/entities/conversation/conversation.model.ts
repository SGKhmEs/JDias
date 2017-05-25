import { Person } from '../person';
import { Message } from '../message';
import { UserAccount } from '../user-account';
export class Conversation {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public subject?: string,
        public createdat?: any,
        public message?: string,
        public participants?: Person,
        public messages?: Message,
        public userAccount?: UserAccount,
    ) {
    }
}
