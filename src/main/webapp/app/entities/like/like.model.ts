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

export class Like implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentGuid?: string,
        public parentType?: Type,
        public positive?: boolean,
        public authorSignature?: string,
        public parentAuthorSignature?: string,
        public post?: BaseEntity,
        public person?: BaseEntity,
    ) {
        this.positive = false;
    }
}
