import { BaseEntity } from './../../shared';

export class Photo implements BaseEntity {
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
        public statusMessage?: BaseEntity,
        public person?: BaseEntity,
    ) {
    }
}
