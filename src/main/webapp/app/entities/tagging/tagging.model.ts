import { Tag } from '../tag';
export class Tagging {
    constructor(
        public id?: number,
        public context?: string,
        public createdAt?: any,
        public tag?: Tag,
    ) {
    }
}
