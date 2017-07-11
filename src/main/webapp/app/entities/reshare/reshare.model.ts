import { Interaction } from '../interaction';
export class Reshare {
    constructor(
        public id?: number,
        public rootAuthor?: string,
        public rootGuid?: string,
        public interaction?: Interaction,
    ) {
    }
}
