import { BaseEntity } from './../../shared';

export class Poll implements BaseEntity {
    constructor(
        public id?: number,
        public guid?: string,
        public question?: string,
        public pollanswers?: BaseEntity[],
        public pollparticipants?: BaseEntity[],
    ) {
    }
}
