import { Interaction } from '../interaction';
import { Author } from '../author';
export class Like {
    constructor(
        public id?: number,
        public post_id?: number,
        public guid?: string,
        public createdAt?: any,
        public interaction?: Interaction,
        public author?: Author,
    ) {
    }
}
