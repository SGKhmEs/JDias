import { Reshare } from '../reshare';
import { Profile } from '../profile';
import { AccountDeletion } from '../account-deletion';
import { Comment } from '../comment';
import { Contact } from '../contact';
import { EventParticipation } from '../event-participation';
import { Participation } from '../participation';
import { Photo } from '../photo';
import { Post } from '../post';
import { Conversation } from '../conversation';
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
        public reshare?: Reshare,
        public profile?: Profile,
        public accountdeletion?: AccountDeletion,
        public comments?: Comment,
        public contacts?: Contact,
        public events?: EventParticipation,
        public participations?: Participation,
        public photos?: Photo,
        public posts?: Post,
        public conversation?: Conversation,
    ) {
        this.closedAccount = false;
    }
}
