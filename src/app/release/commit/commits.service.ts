import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {CommitModel} from './commit.model';

@Injectable()
export class CommitsService {
  private readonly API_URL = 'http://localhost:3000/commits';

  constructor(protected httpClient: HttpClient) {
  }

  public create(corrections: CommitModel): Observable<CommitModel> {
    return this.httpClient.post<CommitModel>(this.API_URL, corrections);
  }

  public getByJiraVersion(jira_version: string): Observable<Array<CommitModel>> {
    return this.httpClient.get<Array<CommitModel>>(`${this.API_URL}?jira_version=${jira_version}`)
      .pipe(catchError((res: HttpErrorResponse) => {
        alert(res.statusText);
        return throwError(res.message);
      }));
  }
}
