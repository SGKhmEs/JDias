import { BaseEntity } from './../../shared';

export class Event implements BaseEntity {
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
        public eventPatricipations?: BaseEntity[],
    ) {
        this.allDay = false;
    }
}
