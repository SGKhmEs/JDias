import { BaseEntity } from './../../shared';

const enum PostType {
    'STATUSMESSAGE',
    'RESHARE'
}

export class AspectVisiblity implements BaseEntity {
    constructor(
        public id?: number,
        public postType?: PostType,
        public aspect?: BaseEntity,
        public post?: BaseEntity,
    ) {
    }
}
