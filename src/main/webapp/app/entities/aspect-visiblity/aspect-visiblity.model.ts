
const enum PostType {
    'STATUSMESSAGE',
    'RESHARE'

};
import { Aspect } from '../aspect';
import { Post } from '../post';
export class AspectVisiblity {
    constructor(
        public id?: number,
        public postType?: PostType,
        public aspect?: Aspect,
        public post?: Post,
    ) {
    }
}
