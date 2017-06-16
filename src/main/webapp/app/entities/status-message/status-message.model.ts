import { Location } from '../location';
import { Poll } from '../poll';
import { Post } from '../post';
import { Photo } from '../photo';
export class StatusMessage {
    constructor(
        public id?: number,
        public text?: string,
        public location?: Location,
        public poll?: Poll,
        public post?: Post,
        public photos?: Photo,
    ) {
    }
}
