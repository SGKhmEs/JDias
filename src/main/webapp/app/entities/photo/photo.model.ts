import { Post } from '../post';
import { Person } from '../person';
export class Photo {
    constructor(
        public id?: number,
        public guid?: boolean,
        public createdAt?: any,
        public remotePhotoPath?: string,
        public remotePhotoName?: string,
        public height?: number,
        public width?: number,
        public text?: string,
        public statusMessageGuid?: string,
        public person?: Person,
    ) {
        this.guid = false;
    }
}
