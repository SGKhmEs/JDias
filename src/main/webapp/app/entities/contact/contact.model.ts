import { BaseEntity } from './../../shared';

export class Contact implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public recipient?: string,
        public following?: boolean,
        public sharing?: boolean,
        public aspectMemberships?: BaseEntity[],
        public person?: BaseEntity,
    ) {
        this.following = false;
        this.sharing = false;
    }
}
