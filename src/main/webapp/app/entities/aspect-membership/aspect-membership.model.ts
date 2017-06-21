import { BaseEntity } from './../../shared';

export class AspectMembership implements BaseEntity {
    constructor(
        public id?: number,
        public createdAt?: any,
        public updatedAt?: any,
        public aspect?: BaseEntity,
        public contact?: BaseEntity,
        public userAccount?: BaseEntity,
    ) {
    }
}
