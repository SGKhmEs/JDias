import { Location } from '../location';
import { Poll } from '../poll';
import { Photo } from '../photo';
export class StatusMessage {
    constructor(
        public id?: number,
        public text?: string,
        public location?: Location,
        public poll?: Poll,
        public photos?: Photo,
    ) {
    }
}
