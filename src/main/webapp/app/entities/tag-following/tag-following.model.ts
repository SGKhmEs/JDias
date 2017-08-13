import { BaseEntity } from './../../shared';

export class TagFollowing implements BaseEntity {
    constructor(
        public id?: number,
        public tag?: BaseEntity,
        public person?: BaseEntity,
    ) {
    }
}
