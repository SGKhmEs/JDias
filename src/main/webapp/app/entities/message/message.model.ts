import { BaseEntity } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public conversationGuid?: string,
        public text?: string,
        public createdAt?: any,
        public conversation?: BaseEntity,
        public person?: BaseEntity,
    ) {
    }
}
