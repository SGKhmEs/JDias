import { BaseEntity } from './../../shared';

export class Tagging implements BaseEntity {
    constructor(
        public id?: number,
        public tag?: BaseEntity,
        public post?: BaseEntity,
    ) {
    }
}
