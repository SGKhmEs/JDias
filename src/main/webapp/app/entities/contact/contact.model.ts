import { Person } from '../person';
import { Aspect } from '../aspect';
export class Contact {
    constructor(
        public id?: number,
        public author?: string,
        public recipient?: string,
        public following?: boolean,
        public sharing?: boolean,
        public ownId?: number,
        public person?: Person,
        public aspect?: Aspect,
    ) {
        this.following = false;
        this.sharing = false;
    }
}
