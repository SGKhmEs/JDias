
const enum EventStatus {
    'ACCEPTED',
    'DECLINED',
    'TENTATIVE'

};
import { Event } from '../event';
import { Person } from '../person';
export class EventParticipation {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentGuid?: string,
        public status?: EventStatus,
        public authorSignature?: string,
        public parentAuthorSignature?: string,
        public event?: Event,
        public person?: Person,
    ) {
    }
}
