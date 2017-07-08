import { BaseEntity } from './../../shared';

export class AccountDeletion implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
    ) {
    }
}
