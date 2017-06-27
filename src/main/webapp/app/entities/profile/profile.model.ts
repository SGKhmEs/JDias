import { Person } from '../person';
export class Profile {
    constructor(
        public id?: number,
        public author?: string,
        public firstName?: string,
        public lastName?: string,
        public imageUrl?: string,
        public imageUrlSmall?: string,
        public imageUrlMedium?: string,
        public birthday?: any,
        public gender?: string,
        public bio?: string,
        public location?: string,
        public searchable?: boolean,
        public nsfw?: boolean,
        public tagString?: string,
        public person?: Person,
    ) {
        this.searchable = false;
        this.nsfw = false;
    }
}
