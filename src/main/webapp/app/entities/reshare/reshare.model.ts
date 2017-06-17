import { Post } from '../post';
export class Reshare {
    constructor(
        public id?: number,
        public rootAuthor?: string,
        public rootGuid?: string,
        public post?: Post,
    ) {
    }
}
