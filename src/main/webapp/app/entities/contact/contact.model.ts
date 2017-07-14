import { AspectMembership } from '../aspect-membership';
import { Person } from '../person';
export class Contact {
    constructor(
        public id?: number,
        public author?: string,
        public recipient?: string,
        public following?: boolean,
        public sharing?: boolean,
        public aspectMemberships?: AspectMembership,
        public person?: Person,
    ) {
        this.following = false;
        this.sharing = false;
    }
}
