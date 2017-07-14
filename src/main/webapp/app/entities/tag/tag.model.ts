import { Post } from '../post';
import { TagFollowing } from '../tag-following';
import { Tagging } from '../tagging';
export class Tag {
    constructor(
        public id?: number,
        public name?: string,
        public post?: Post,
        public tagFollowings?: TagFollowing,
        public taggings?: Tagging,
        public createdAt?: any,
        public updatedAt?: any
    ) {
    }
}
