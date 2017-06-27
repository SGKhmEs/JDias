import { Location } from '../location';
import { Poll } from '../poll';
import { Post } from '../post';
import { Photo } from '../photo';

    export class StatusMessage {
        constructor(
            public id?: number,
            public text?: string,
            public location?: Location,
        ) {
    }
}

    export class StatusMessageDTO {
        constructor(
            public id?: number,
            public status_message?: StatusMessage,
            public aspect_ids?: string[],
            public photos?: string[],
            public location_address?: string,
            public location_coords?: string,
            public poll_question?: string,
            public poll_answers?: string[],
        ) {
        }
    }

/*
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
}*/
