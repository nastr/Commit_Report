import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError, map} from 'rxjs/operators';
import {ReleaseModel} from './release.model';

@Injectable()
export class ReleasesService {
  private readonly API_URL = 'http://localhost:3000/releases';

  dataChange: BehaviorSubject<Array<ReleaseModel>> = new BehaviorSubject<Array<ReleaseModel>>([]);
  dialogData: any;

  constructor(private httpClient: HttpClient) {
  }

  get data(): Array<ReleaseModel> {
    return this.dataChange.value;
  }

  getDialogData() {
    return this.dialogData;
  }

  public get(jira_version: string): Observable<ReleaseModel> {
    return this.httpClient.get<ReleaseModel>(`${this.API_URL}?jira_version=${jira_version}`)
      .pipe(catchError((res: HttpErrorResponse) => {
        alert(res.statusText);
        return throwError(res.message);
      }));
  }

  /** CRUD METHODS */
  getAll(): void {
    this.httpClient.get<ReleaseModel[]>(this.API_URL + '/').subscribe(data => {
        this.dataChange.next(data);
      },
      (error: HttpErrorResponse) => {
        console.log(error.name + ' ' + error.message);
      });
  }

  // ADD, POST METHOD
  add(release: ReleaseModel): void {
    this.httpClient.post(this.API_URL + '/', release).subscribe(data => {
        this.dialogData = release;
        // this.toasterService.showToaster('INFO', 'INFO', 'Successfully added');
      },
      (err: HttpErrorResponse) => {
        console.log(err);
        // this.toasterService.showToaster('ERROR', 'ERROR', 'Error occurred. Details: ' + err.name + ' ' + err.message);
      });
  }

  // UPDATE, PUT METHOD
  update(release: ReleaseModel): void {
    this.httpClient.put(this.API_URL + '/' + release.jira_version, release).subscribe(data => {
        this.dialogData = release;
        // this.toasterService.showToaster('INFO', 'INFO', 'Successfully added');
      },
      (err: HttpErrorResponse) => {
        console.log(err);
      }
    );
  }

  // DELETE METHOD
  delete(jira_version: string): void {
    this.httpClient.delete(this.API_URL + '/' + jira_version).subscribe(data => {
        // console.log(data['']);
        // this.toasterService.showToaster('INFO', 'INFO', 'Successfully added');
      },
      (err: HttpErrorResponse) => {
        console.log(err);
      }
    );
  }
}
