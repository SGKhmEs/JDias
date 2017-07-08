import { BaseEntity } from './../../shared';

export class Tagging implements BaseEntity {
    constructor(
        public id?: number,
        public context?: string,
        public createdAt?: any,
        public tag?: BaseEntity,
    ) {
    }
}
