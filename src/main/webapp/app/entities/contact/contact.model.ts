import { BaseEntity } from './../../shared';

export class Contact implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public recipient?: string,
        public following?: boolean,
        public sharing?: boolean,
        public ownId?: number,
        public person?: BaseEntity,
        public aspect?: BaseEntity,
    ) {
        this.following = false;
        this.sharing = false;
    }
}
