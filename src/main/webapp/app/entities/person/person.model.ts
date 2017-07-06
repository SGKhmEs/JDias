import { Profile } from '../profile';
import { AccountDeletion } from '../account-deletion';
import { Contact } from '../contact';
import { Post } from '../post';
import { Photo } from '../photo';
import { Comment } from '../comment';
import { Participation } from '../participation';
import { EventParticipation } from '../event-participation';
import { Message } from '../message';
import { Conversation } from '../conversation';
import { UserAccount } from '../user-account';
import { Aspect } from '../aspect';
export class Person {
    constructor(
        public id?: number,
        public guid?: string,
        public diasporaId?: string,
        public serializedPublicKey?: string,
        public createdAt?: any,
        public updatedAt?: any,
        public closedAccount?: boolean,
        public fetchStatus?: number,
        public podId?: number,
        public profile?: Profile,
        public accountdeletion?: AccountDeletion,
        public contacts?: Contact,
        public posts?: Post,
        public photos?: Photo,
        public comments?: Comment,
        public participations?: Participation,
        public events?: EventParticipation,
        public message?: Message,
        public conversation?: Conversation,
        public userAccount?: UserAccount,
        public aspect?: Aspect,
    ) {
        this.closedAccount = false;
    }
}
