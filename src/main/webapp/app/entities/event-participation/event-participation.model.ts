
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
        public parentguid?: string,
        public status?: EventStatus,
        public authorsignature?: string,
        public parentauthorsignature?: string,
        public event?: Event,
        public person?: Person,
    ) {
    }
}
