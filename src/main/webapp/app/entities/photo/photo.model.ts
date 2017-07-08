import { StatusMessage } from '../status-message';
import { Person } from '../person';
export class Photo {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public createdAt?: any,
        public remotePhotoPath?: string,
        public remotePhotoName?: string,
        public height?: number,
        public width?: number,
        public text?: string,
        public statusMessageGuid?: string,
        public thumb_small?: string,
        public thumb_medium?: string,
        public thumb_large?: string,
        public scaled_full?: string,
        public statusMessage?: StatusMessage,
        public person?: Person,
    ) {
    }
}
