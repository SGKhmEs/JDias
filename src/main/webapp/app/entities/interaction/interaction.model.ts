import { Like } from '../like';
import { Reshare } from '../reshare';
import { Comment } from '../comment';
export class Interaction {
    constructor(
        public id?: number,
        public likes_count?: number,
        public reshares_count?: number,
        public comments_count?: number,
        public likes?: Like,
        public reshares?: Reshare,
        public comments?: Comment,
    ) {
    }
}
