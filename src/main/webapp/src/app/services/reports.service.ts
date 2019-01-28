import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {ReleaseModel} from '../models/release.model';

@Injectable()
export class ReportsService {
    private readonly API_URL = 'http://localhost:8080/reports';
    // private readonly API_URL = 'http://localhost:3000/reports';
    // private readonly API_URL = '/reports';

    dataChange: BehaviorSubject<Array<ReleaseModel>> = new BehaviorSubject<Array<ReleaseModel>>([]);
    dialogData: any;

    constructor(private httpClient: HttpClient) {
    }

    get data(): Array<ReleaseModel> {
        return this.dataChange.value;
    }

    /*getDialogData() {
        return this.dialogData;
    }*/

    public get(jiraVersion: string): Observable<ReleaseModel> {
        // return this.httpClient.get<ReleaseModel>(`${this.API_URL}?jira_version=${jiraVersion}`)
        return this.httpClient.get<ReleaseModel>(`${this.API_URL}` + "/" + jiraVersion)
            .pipe(catchError((res: HttpErrorResponse) => {
                alert(res.statusText);
                return throwError(res.message);
            }));
    }

    public list(): Observable<Array<ReleaseModel>> {
        return this.httpClient.get<Array<ReleaseModel>>(this.API_URL);
    }

    /*list(urlOrFilter?: string | object): Observable<Page<ReleaseModel>> {
        return this.queryPaginated<ReleaseModel>(this.httpClient, this.API_URL, urlOrFilter);
    }

    queryPaginated<T>(http: HttpClient, baseUrl: string, urlOrFilter?: string | object): Observable<Page<T>> {
        let params = new HttpParams();
        let url = baseUrl;

        if (typeof urlOrFilter === 'string') {
            // we were given a page URL, use itObservable
            url = urlOrFilter;
        } else if (typeof urlOrFilter === 'object') {
            // we were given filtering criteria, build the query string
            Object.keys(urlOrFilter).sort().forEach(key => {
                const value = urlOrFilter[key];
                if (value !== null) {
                    params = params.set(key, value.toString());
                }
            });
        }

        return http.get<Page<T>>(url, {
            params: params
        });
    }*/

    add(release: ReleaseModel): void {
        this.get(release.jira_version).subscribe(r => {
            if (r[0]) {
                throwError("this jira_version already exist");
                console.log("this jira_version already exist");
                return;
            } else {
                this.httpClient.post(this.API_URL + '/', release).subscribe(data => {
                        this.dialogData = release;
                    },
                    (err: HttpErrorResponse) => {
                        console.log(err);
                    });
            }
        });
    }

    update(release: ReleaseModel): void {
        if (!release.id) {
            this.get(release.jira_version).subscribe(r => {
                if (r[0]) {
                    release.id = r[0].id;
                    release.status = r[0].status;
                    release.updated = r[0].updated;
                    this.httpClient.put(this.API_URL + '/' + release.id, release).subscribe(() => {
                            this.dialogData = release;
                            // console.log(release.id);
                        },
                        (err: HttpErrorResponse) => {
                            console.log(err);
                        }
                    );
                }
            });
        }
    }

    delete(jira_version: string): void {
        this.get(jira_version).subscribe(r => {
            if (r[0]) {
                // console.log(id);
                this.httpClient.delete(this.API_URL + '/' + r[0].id).subscribe(() => {
                    },
                    (err: HttpErrorResponse) => {
                        console.log(err);
                    }
                );
            }
        });
    }

}
