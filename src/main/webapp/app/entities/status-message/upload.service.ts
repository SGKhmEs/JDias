import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UploadService {
    progress: number;
    progressObserver: any;
    progress$: any;

    constructor() {
        this.progress$ = Observable.create((observer) => {
            this.progressObserver = observer;
        }).share();
    }

    makeFileRequest(url: string, params: string[], files: File[]): Observable<any> {
        return Observable.create((observer) => {
            const formData: FormData = new FormData(),
                xhr: XMLHttpRequest = new XMLHttpRequest();

            for (let i = 0; i < files.length; i++) {
                formData.append('uploads[]', files[i], files[i].name);
            }

            xhr.onreadystatechange = () => {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        observer.next(JSON.parse(xhr.response));
                        observer.complete();
                    } else {
                        observer.error(xhr.response);
                    }
                }
            };

            xhr.upload.onprogress = (event) => {
                this.progress = Math.round(event.loaded / event.total * 100);

                this.progressObserver.next(this.progress);
            };
            // const token = ('meta[name=csrf-token]').attr('content');
            xhr.open('POST', url, true);
            // xhr.setRequestHeader('X-XSRF-TOKEN', '2d4d0208-fb3f-49f4-b9a5-f092f2783314');
            xhr.send(formData);
        });
    }
}
