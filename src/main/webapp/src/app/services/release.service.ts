import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {map, catchError} from 'rxjs/operators';
import {CorrectionModel} from "../models/correction.model";
import {CorrectionRequestModel} from "../models/correction-request.model";


@Injectable()
export class ReleaseService {
    // private readonly API_URL = 'http://localhost:3000/db';
    private readonly API_URL = 'http://localhost:8080/release';

    constructor(protected httpClient: HttpClient) {
    }

    public create(corrections: CorrectionRequestModel) {
        return this.httpClient.post<CorrectionModel>(this.API_URL, corrections).subscribe();
    }

    public getData(jiraVersion: string) {
        return this.httpClient.get(`${this.API_URL}` + "/" + jiraVersion);
        // .pipe(map((val: any) => val['jiras']));
    }
}
