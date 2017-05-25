import { Post } from '../post';
import { Person } from '../person';
export class Comment {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentguid?: string,
        public text?: string,
        public createdat?: any,
        public authorsignature?: string,
        public parentauthorsignature?: string,
        public threadparentguid?: string,
        public post?: Post,
        public person?: Person,
    ) {
    }
}
