import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { StatusMessage, StatusMessageDTO } from './status-message.model';
import { StatusMessageService } from './status-message.service';
import { Principal, Account, ResponseWrapper } from '../../shared';
import {Observable} from 'rxjs/Observable';
import { Http, Response } from '@angular/http';
import {Photo} from '../photo/photo.model';
import {Aspect} from '../aspect/aspect.model';
import {PersonService} from '../person/person.service';

@Component({
    selector: 'jhi-status-message',
    templateUrl: './status-message.component.html',
    styleUrls: ['../../../content/scss/sm.css'],
})

export class StatusMessageComponent implements OnInit, OnDestroy {

    //#region Variables
     resourceUrl = '/api/file';
     imgUrl = '/api/files/';
     personUrl = 'api/people/get';
     statusM: StatusMessage = new StatusMessage();
     statusMessage: StatusMessageDTO = new StatusMessageDTO();
     inputAnswers: string[] = ['', ''];
     statusMessages: StatusMessage[];
     photos: Photo[] = [];
     aspects: Aspect[] = [];
     src: string[] = [];
     currentAccount: Account;
     eventSubscriber: Subscription;
     currentSearch: string;
     isLocation = true;
     isPhoto = true;
     isPoll = true;
     lat: number;
     lng: number;
     isSaving: boolean;
     isShowAspects = false;
     isShareDisabled = false;
     fileName: string;
     options = {
        enableHighAccuracy: true,
        timeout: 3000,
        maximumAge: 0
    };

    //#endregion

    //#region Constructor
    constructor(
        private personService: PersonService,
        private statusMessageService: StatusMessageService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private http: Http
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }
    //#endregion

    //#region Aspects
    showAspects() {
        this.isShowAspects = !this.isShowAspects;
    }

    load() {
        this.findPerson().subscribe((aspect) => {
            this.aspects = aspect;
            console.log(this.aspects);
        });
    }

    findPerson(): Observable<Aspect[]> {
        return this.http.get(`${this.personUrl}/${this.currentAccount.login}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.personService.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    //#endregion

    //#region Photos

    removeImage(url: string) {
        const fileName = url.replace(/^.*[\\\/]/, '');
        this.http.delete(`${this.imgUrl}${fileName}`).subscribe((response) => {
            this.alertService.success('jDiasApp.statusMessage.deleted', {param: this.fileName}, null);
            this.eventManager.broadcast({
                name: 'image',
                content: 'Deleted an image'
            });
            for (let i = 0; i < this.src.length; i++) {
                if (this.src[i] === url) {
                    this.src.splice(i, 1);
                }
            }
            if (this.src.length === 0) {
                this.isPhoto = true;
            }
            return response.json();
        });
    }

    onChange(event) {
        console.log('onChange');
        const fileList: FileList = event.target.files;
        const formData: FormData = new FormData();
        if (fileList.length > 0) {
            for (let i = 0; i < fileList.length; i++) {
                formData.append('file', fileList[i], fileList[i].name);
            }
        }
        this.subscribeToSavePhotoResponse(this.fileChange(formData), true);
    }

    fileChange(formData: FormData): Observable<Photo> {
        return this.http.post(this.resourceUrl, formData).map((res: Response) => {
            return res.json();
        });
    }

    private subscribeToSavePhotoResponse(result: Observable<Photo[]>, isCreated: boolean) {
        result.subscribe((res: Photo[]) =>
            this.onSavePhotoSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSavePhotoSuccess(result: Photo[], isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.post.created'
                : 'jDiasApp.post.updated',
            { param : result[0].id }, null);
        this.isPhoto = false;
        for (const p of result) {
            this.photos.push(p);
            const fileName = p.thumb_medium.replace(/^.*[\\\/]/, '');
            this.showImage(fileName);
        }
        this.eventManager.broadcast({ name: 'postListModification', content: 'OK'});
        this.isSaving = false;
    }

    showImage(filename: string) {
         this.getImage(filename)
            .subscribe((file) => {
             console.log(file);
             const s: string = file;
                this.src.push(s);
                for (const g of this.src){
                    console.log(g);
                }
            });
    }

    getImage(filename: String): Observable<any> {
        return this.http.get(this.imgUrl + filename)
            .map(this.extractUrl);
    }

    extractUrl(res: Response): string {
        return res.url;
    }

    //#endregion

    //#region Location
    getAdress(): any {
        return this.http.get('https://nominatim.openstreetmap.org/reverse?format=json&lat=' + this.lat +
            '&lon=' + this.lng + '&addressdetails=3').subscribe((res: any) => {
            this.statusMessage.location_address = res.json()['display_name'];
            console.log(this.statusMessage.location_address);
            this.isLocation = false;
            this.eventManager.broadcast({name: 'locationListModification', content: 'OK'});
            this.isSaving = false;
            return res.json();
        });
    }

    removeLocation() {
        this.statusMessage.location_address = null;
        this.isLocation = true;
    }

    getLocation() {
        navigator.geolocation.getCurrentPosition(this.successCallback, this.errorCallback, this.options);
    }

    successCallback = (position) => {
        this.lat = position.coords.latitude;
        this.lng = position.coords.longitude;
        console.log(this.lng, this.lat);
        this.getAdress();
        // this.subscribeToSaveResponse(this.getAdress(), true);
    }

    errorCallback = (error) => {
        let errorMessage = 'Unknown error';
        switch (error.code) {
            case 1:
                errorMessage = 'Permission denied';
                break;
            case 2:
                errorMessage = 'Position unavailable';
                break;
            case 3:
                errorMessage = 'Timeout';
                break;
        }
        console.log(errorMessage);
    }

    private subscribeToSaveResponse(result: any, isCreated: boolean) {
        result.subscribe((res: any) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: any, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.location.created'
                : 'jDiasApp.location.updated',
            {param: result['display_name']}, null);

        this.statusMessage.location_address = result['display_name'];
        console.log(this.statusMessage.location_address);
        this.isLocation = false;
        this.eventManager.broadcast({name: 'locationListModification', content: 'OK'});
        this.isSaving = false;
    }
    //#endregion

    //#regionStatusMessage

    saveStatusMessage() {
        for (let i = 0; i < this.inputAnswers.length; i++) {
            if (this.inputAnswers[i] === '') {
                this.inputAnswers.splice(i, 1);
            }
        }
        this.statusMessage.photos = [];
        for (let i = 0; i < this.photos.length; i++) {
            this.statusMessage.photos.push(this.photos[i].id);
        }
        this.statusMessage.poll_answers = this.inputAnswers;
        this.statusMessage.location_coords = this.lat + ', ' + this.lng;
        // this.statusMessage.photos = [];
        this.statusMessage.aspect_ids = [];
        this.statusMessage.status_message = this.statusM;
        this.isSaving = true;
       // this.statusMessage.text = 'test';
        if (this.statusMessage.status_message.id !== undefined) {
            this.subscribeToSaveStatusMessageResponse(
                this.statusMessageService.update(this.statusMessage), false);
        } else {
            this.subscribeToSaveStatusMessageResponse(
                this.statusMessageService.create(this.statusMessage), true);
        }
    }

    private subscribeToSaveStatusMessageResponse(result: Observable<StatusMessageDTO>, isCreated: boolean) {
        result.subscribe((res: StatusMessageDTO) =>
            this.onSaveStatusMessageSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveStatusMessageSuccess(result: StatusMessage, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'jDiasApp.statusMessage.created'
                : 'jDiasApp.statusMessage.updated',
            { param : result.id }, null);
        this.statusMessageService.find(result.id).subscribe((statusmessage) => {});
        this.eventManager.broadcast({ name: 'statusMessageListModification', content: 'OK'});
        this.isSaving = false;
    }

    //#endregion

    //#region Poll Question

    createPoll() {
        this.isPoll = !this.isPoll;
        if (this.isPoll) {
            this.inputAnswers = ['', ''];
        }
    }

    pushArrayValue() {
       const length = this.inputAnswers.length - 1;
       if (this.inputAnswers[length] !== '') {
           this.inputAnswers.push('');
       }
    }

    trackPoll(index: any, item: any) {
        return index;
    }

    //#endregion

    //#region Other

    loadAll() {
        if (this.currentSearch) {
            this.statusMessageService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.statusMessages = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.statusMessageService.query().subscribe(
            (res: ResponseWrapper) => {
                this.statusMessages = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.isSaving = false;
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            console.log(this.currentAccount);
            this.load();
        });
        this.registerChangeInStatusMessages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: StatusMessage) {
        return item.id;
    }

    trackAspect(index: number, item: Aspect) {
        return item.id;
    }

    trackPhoto(index: any, item: any) {
        return index;
    }

    registerChangeInStatusMessages() {
        this.eventSubscriber = this.eventManager.subscribe('statusMessageListModification', (response) => this.loadAll());
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    //#endregion
}
