import { BaseEntity } from './../../shared';

export class Aspect implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public createdAt?: any,
        public updatedAt?: any,
        public contactVisible?: boolean,
        public chatEnabled?: boolean,
        public postDefault?: boolean,
        public aspectVisibilities?: BaseEntity[],
        public contacts?: BaseEntity[],
        public person?: BaseEntity,
    ) {
        this.contactVisible = false;
        this.chatEnabled = false;
        this.postDefault = false;
    }
}
