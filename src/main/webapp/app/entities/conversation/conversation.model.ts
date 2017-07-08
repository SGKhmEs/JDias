import { BaseEntity } from './../../shared';

export class Conversation implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public subject?: string,
        public createdAt?: any,
        public message?: string,
        public updatedAt?: any,
        public messages?: BaseEntity[],
        public participants?: BaseEntity[],
    ) {
    }
}
