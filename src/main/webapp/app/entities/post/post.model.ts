
const enum PostType {
    'STATUSMESSAGE',
    'RESHARE'

};
import { StatusMessage } from '../status-message';
import { Reshare } from '../reshare';
import { Comment } from '../comment';
import { AspectVisiblity } from '../aspect-visiblity';
import { Like } from '../like';
import { Tag } from '../tag';
import { Person } from '../person';

import { Author } from '../author';
import { Photo } from '../photo';
import { Interaction } from '../interaction';

export class Post {
    constructor(
        public id?: number,
        public text?: string,
        public guid?: string,
        public createdAt?: any,
        public created_at?: any,
        public postType?: PostType,
        public posttype?: string,
        public providerDisplayName?: string,
        public pub?: boolean,

        public statusMessage?: StatusMessage,
        public reshare?: Reshare,
        public comments?: Comment,
        public aspectVisiblities?: AspectVisiblity,
        public likes?: Like,
        public tags?: Tag,

        public nsfw?: boolean,
        public oembedcache?: string,
        public participation?: string,
        public alreadyparticipatedinpoll?: boolean,
        public createdat?: any,
        public interactedat?: any,
        public mentionedpeople?: string,

        public root?: Post,
        public author?: Author,
        public person?: Person,
        public interactions?: Interaction,
        public photos?: Photo,
    ) {
        this.pub = false;
        this.alreadyparticipatedinpoll = false;
        this.pub = false;
    }
}
