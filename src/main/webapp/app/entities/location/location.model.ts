import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public address?: string,
        public lat?: number,
        public lng?: number,
    ) {
    }
}
