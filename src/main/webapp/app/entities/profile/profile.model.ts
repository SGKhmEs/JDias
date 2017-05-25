export class Profile {
    constructor(
        public id?: number,
        public author?: string,
        public firstname?: string,
        public lastname?: string,
        public imageurl?: string,
        public imageurlsmall?: string,
        public imageurlmedium?: string,
        public birthday?: any,
        public gender?: string,
        public bio?: string,
        public location?: string,
        public searchable?: boolean,
        public nsfw?: boolean,
        public tagstring?: string,
    ) {
        this.searchable = false;
        this.nsfw = false;
    }
}
