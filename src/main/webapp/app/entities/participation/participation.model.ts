
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
import { Person } from '../person';
export class Participation {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentGuid?: string,
        public parentType?: Type,
        public person?: Person,
    ) {
    }
}
