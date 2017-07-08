import { BaseEntity } from './../../shared';

export class Photo implements BaseEntity {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: boolean,
        public createdAt?: any,
        public remotePhotoPath?: string,
        public remotePhotoName?: string,
        public height?: number,
        public width?: number,
        public text?: string,
        public statusMessageGuid?: string,
        public statusMessage?: BaseEntity,
        public person?: BaseEntity,
    ) {
        this.guid = false;
    }
}
