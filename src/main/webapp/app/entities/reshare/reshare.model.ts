import { Post } from '../post';
import { Person } from '../person';
export class Reshare {
    constructor(
        public id?: number,
        public rootAuthor?: string,
        public rootGuid?: string,
        public post?: Post,
        public person?: Person,
    ) {
    }
}
