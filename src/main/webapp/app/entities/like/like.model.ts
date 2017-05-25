
const enum Type {
    'ACCOUNTDELETION',
    'COMMENT',
    'CONTACT',
    'CONVERSATION',
    'EVENT',
    'EVENTPARTICIPATION',
    'LIKE',
    'LOCATION',
    'MESSAGE',
    'PARTICIPATION',
    'PHOTO',
    'POLL',
    'POLLANSWER',
    'POLLPARTICIPATION',
    'POST',
    'PROFILE',
    'RESHARE',
    'RETRACTION',
    'STATUSMESSAGE'

};
export class Like {
    constructor(
        public id?: number,
        public author?: string,
        public guid?: string,
        public parentguid?: string,
        public parenttype?: Type,
        public positive?: boolean,
        public authorsignature?: string,
        public parentauthorsignature?: string,
    ) {
        this.positive = false;
    }
}
