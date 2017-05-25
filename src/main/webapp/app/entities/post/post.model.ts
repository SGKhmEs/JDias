
const enum PostType {
    'STATUSMESSAGE',
    'RESHARE'

};
import { StatusMessage } from '../status-message';
import { Reshare } from '../reshare';
import { Comment } from '../comment';
import { Person } from '../person';
export class Post {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public createdat?: any,
        public pub?: boolean,
        public providerdisplayname?: string,
        public posttype?: PostType,
        public statusMessage?: StatusMessage,
        public reshare?: Reshare,
        public comments?: Comment,
        public person?: Person,
    ) {
        this.pub = false;
    }
}
