import { BaseEntity } from './../../shared';

const enum PostType {
    'STATUSMESSAGE',
    'RESHARE'
}

export class Post implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public createdAt?: any,
        public pub?: boolean,
        public providerDisplayName?: string,
        public postType?: PostType,
        public aspectVisiblities?: BaseEntity[],
        public comments?: BaseEntity[],
        public likes?: BaseEntity[],
        public person?: BaseEntity,
        public reshare?: BaseEntity,
        public statusMessage?: BaseEntity,
        public taggings?: BaseEntity[],
    ) {
        this.pub = false;
    }
}
