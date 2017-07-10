import { BaseEntity } from './../../shared';

export class Tag implements BaseEntity {
    constructor(
        public id?: number,
        public tagContext?: string,
        public createdAt?: any,
        public updatedAt?: any,
        public taggings?: BaseEntity[],
        public tagFollowings?: BaseEntity[],
        public hashTag?: BaseEntity,
    ) {
    }
}
