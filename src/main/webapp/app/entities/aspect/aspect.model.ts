import { AspectMembership } from '../aspect-membership';
import { AspectVisiblity } from '../aspect-visiblity';
import {Person} from '../person/person.model';
export class Aspect {
    constructor(
        public id?: number,
        public name?: string,
        public createdAt?: any,
        public updatedAt?: any,
        public contactVisible?: boolean,
        public chatEnabled?: boolean,
        public postDefault?: boolean,
        public person?: Person,
        public aspectMemberships?: AspectMembership,
        public aspectVisibilities?: AspectVisiblity,
    ) {
        this.contactVisible = false;
        this.chatEnabled = false;
        this.postDefault = false;
    }
}
