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
        public statusMessage?: BaseEntity,
        public reshare?: BaseEntity,
        public comments?: BaseEntity[],
        public aspectVisiblitis?: BaseEntity[],
        public likes?: BaseEntity[],
        public tags?: BaseEntity[],
        public person?: BaseEntity,
    ) {
        this.pub = false;
    }
}
