import { BaseEntity } from './../../shared';

export class Comment implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentGuid?: string,
        public text?: string,
        public createdAt?: any,
        public authorSignature?: string,
        public parentAuthorSignature?: string,
        public threadParentGuid?: string,
        public post?: BaseEntity,
        public person?: BaseEntity,
    ) {
    }
}
