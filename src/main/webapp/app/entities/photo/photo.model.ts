import { Post } from '../post';
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
        public post?: Post,
    ) {
        this.guid = false;
    }
}
