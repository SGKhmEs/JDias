import { Tag } from '../tag';
import { UserAccount } from '../user-account';
export class TagFollowing {
    constructor(
        public id?: number,
        public createdAt?: any,
        public updatedAt?: any,
        public tag?: Tag,
        public userAccount?: UserAccount,
    ) {
    }
}
