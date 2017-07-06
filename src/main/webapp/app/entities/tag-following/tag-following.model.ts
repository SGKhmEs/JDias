import { BaseEntity } from './../../shared';

export class TagFollowing implements BaseEntity {
    constructor(
        public id?: number,
        public createdAt?: any,
        public updatedAt?: any,
        public tag?: BaseEntity,
        public userAccount?: BaseEntity,
    ) {
    }
}
