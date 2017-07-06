import { User } from '../../shared';
import { Person } from '../person';
import { TagFollowing } from '../tag-following';
export class UserAccount {
    constructor(
        public id?: number,
        public serializedPrivateKey?: string,
        public gettingStarted?: boolean,
        public disableMail?: boolean,
        public language?: string,
        public rememberCreatedAt?: any,
        public signInCount?: number,
        public currentSignInAt?: any,
        public lastSignInAt?: any,
        public currentSignInIp?: string,
        public lastSignInIp?: string,
        public createdAt?: any,
        public updatedAt?: any,
        public lockedAt?: any,
        public showCommunitySpotlightInStream?: boolean,
        public autoFollowBack?: boolean,
        public autoFollowBackAspectId?: number,
        public hiddenShareables?: string,
        public lastSeen?: any,
        public exportE?: string,
        public exportedAt?: any,
        public exporting?: boolean,
        public stripExif?: boolean,
        public exportedPhotosFile?: string,
        public exportedPhotosAt?: any,
        public exportingPhotos?: boolean,
        public colorTheme?: string,
        public postDefaultPublic?: boolean,
        public user?: User,
        public person?: Person,
        public tagfollowings?: TagFollowing,
    ) {
        this.gettingStarted = false;
        this.disableMail = false;
        this.showCommunitySpotlightInStream = false;
        this.autoFollowBack = false;
        this.exporting = false;
        this.stripExif = false;
        this.exportingPhotos = false;
        this.postDefaultPublic = false;
    }
}
