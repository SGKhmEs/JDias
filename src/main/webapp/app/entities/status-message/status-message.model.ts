import { BaseEntity } from './../../shared';

export class StatusMessage implements BaseEntity {
    constructor(
        public id?: number,
        public text?: string,
        public location?: BaseEntity,
        public poll?: BaseEntity,
        public posts?: BaseEntity[],
        public photos?: BaseEntity[],
    ) {
    }
}
