import { StatusMessage } from '../status-message';
import { Person } from '../person';
export class Photo {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: boolean,
        public createdat?: any,
        public remotephotopath?: string,
        public remotephotoname?: string,
        public height?: number,
        public width?: number,
        public text?: string,
        public statusmessageguid?: string,
        public statusMessage?: StatusMessage,
        public person?: Person,
    ) {
        this.guid = false;
    }
}
