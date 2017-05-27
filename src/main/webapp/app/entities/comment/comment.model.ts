import { Post } from '../post';
import { Person } from '../person';
export class Comment {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentGuid?: string,
        public text?: string,
        public createdAt?: any,
        public authorSignature?: string,
        public parentAuthorSignature?: string,
        public threadParentGuid?: string,
        public post?: Post,
        public person?: Person,
    ) {
    }
}
