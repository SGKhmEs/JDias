import { Aspect } from '../aspect';
import { Contact } from '../contact';
import { UserAccount } from '../user-account';
export class AspectMembership {
    constructor(
        public id?: number,
        public createdAt?: any,
        public updatedAt?: any,
        public aspect?: Aspect,
        public contact?: Contact,
        public userAccount?: UserAccount,
    ) {
    }
}
