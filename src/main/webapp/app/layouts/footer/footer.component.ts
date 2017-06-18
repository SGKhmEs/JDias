import { Component } from '@angular/core';
import { Principal} from '../../shared';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html'
})
export class FooterComponent {
    constructor(private principal: Principal) {}

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

}
