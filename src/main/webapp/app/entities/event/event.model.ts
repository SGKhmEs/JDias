import { EventParticipation } from '../event-participation';
export class Event {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public summary?: string,
        public start?: any,
        public end?: any,
        public allDay?: boolean,
        public timezone?: string,
        public description?: string,
        public eventPatricipations?: EventParticipation,
    ) {
        this.allDay = false;
    }
}
