import { BaseEntity } from './../../shared';

export class Tag implements BaseEntity {
    constructor(
        public id?: number,
        public tagContext?: string,
        public posts?: BaseEntity[],
        public person?: BaseEntity,
    ) {
    }
}
