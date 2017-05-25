import { EventParticipation } from '../event-participation';
export class Event {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public summary?: string,
        public start?: any,
        public end?: any,
        public allday?: boolean,
        public timezone?: string,
        public description?: string,
        public events2?: EventParticipation,
    ) {
        this.allday = false;
    }
}
