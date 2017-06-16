
const enum PostType {
    'STATUSMESSAGE',
    'RESHARE'

};
import { AspectVisiblity } from '../aspect-visiblity';
import { Comment } from '../comment';
import { Like } from '../like';
import { Tag } from '../tag';
import { Person } from '../person';
import { Reshare } from '../reshare';
import { StatusMessage } from '../status-message';
export class Post {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public createdAt?: any,
        public pub?: boolean,
        public providerDisplayName?: string,
        public postType?: PostType,
        public aspectVisiblities?: AspectVisiblity,
        public comments?: Comment,
        public likes?: Like,
        public tags?: Tag,
        public person?: Person,
        public reshare?: Reshare,
        public statusMessage?: StatusMessage,
    ) {
        this.pub = false;
    }
}
