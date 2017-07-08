import { BaseEntity } from './../../shared';

const enum EventStatus {
    'ACCEPTED',
    'DECLINED',
    'TENTATIVE'
}

export class EventParticipation implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentGuid?: string,
        public status?: EventStatus,
        public authorSignature?: string,
        public parentAuthorSignature?: string,
        public event?: BaseEntity,
        public person?: BaseEntity,
    ) {
    }
}
