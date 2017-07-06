import { BaseEntity } from './../../shared';

export class Reshare implements BaseEntity {
    constructor(
        public id?: number,
        public rootAuthor?: string,
        public rootGuid?: string,
    ) {
    }
}
