import { AspectVisibility } from '../aspect-visibility';
import { Contact } from '../contact';
import { Person } from '../person';
export class Aspect {
    constructor(
        public id?: number,
        public name?: string,
        public createdAt?: any,
        public updatedAt?: any,
        public contactVisible?: boolean,
        public chatEnabled?: boolean,
        public postDefault?: boolean,
        public aspectVisibilities?: AspectVisibility,
        public contact?: Contact,
        public person?: Person,
    ) {
        this.contactVisible = false;
        this.chatEnabled = false;
        this.postDefault = false;
    }
}
