import { BaseEntity } from './../../shared';

export class Person implements BaseEntity {
    constructor(
        public id?: number,
        public guid?: string,
        public diasporaId?: string,
        public serializedPublicKey?: string,
        public createdAt?: any,
        public updatedAt?: any,
        public closedAccount?: boolean,
        public fetchStatus?: number,
        public podId?: number,
        public profile?: BaseEntity,
        public accountdeletion?: BaseEntity,
        public contacts?: BaseEntity[],
        public posts?: BaseEntity[],
        public photos?: BaseEntity[],
        public comments?: BaseEntity[],
        public participations?: BaseEntity[],
        public events?: BaseEntity[],
        public messages?: BaseEntity[],
        public conversations?: BaseEntity[],
        public userAccount?: BaseEntity,
        public aspects?: BaseEntity[],
    ) {
        this.closedAccount = false;
    }
}
