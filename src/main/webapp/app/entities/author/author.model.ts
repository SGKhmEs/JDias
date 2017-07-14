import { Avatar } from '../avatar';
export class Author {
    constructor(
        public id?: number,
        public diaspora_id?: string,
        public guid?: string,
        public avatar?: Avatar,
    ) {
    }
}
