
const enum Type {
    'ACCOUNTDELETION',
    'COMMENT',
    'CONTACT',
    'CONVERSATION',
    'EVENT',
    'EVENTPARTICIPATION',
    'LIKE',
    'LOCATION',
    'MESSAGE',
    'PARTICIPATION',
    'PHOTO',
    'POLL',
    'POLLANSWER',
    'POLLPARTICIPATION',
    'POST',
    'PROFILE',
    'RESHARE',
    'RETRACTION',
    'STATUSMESSAGE'

};
import { Post } from '../post';
import { Person } from '../person';
export class Like {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentGuid?: string,
        public parentType?: Type,
        public positive?: boolean,
        public authorSignature?: string,
        public parentAuthorSignature?: string,
        public post?: Post,
        public person?: Person,
    ) {
        this.positive = false;
    }
}
