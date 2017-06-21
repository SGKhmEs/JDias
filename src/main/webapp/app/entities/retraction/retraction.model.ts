import { BaseEntity } from './../../shared';

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
}

export class Retraction implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public targetGuid?: string,
        public targetType?: Type,
    ) {
    }
}
