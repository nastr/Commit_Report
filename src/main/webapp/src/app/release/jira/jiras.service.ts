import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {JiraModel} from './jira.model';

@Injectable()
export class JirasService {
  private readonly API_URL = 'http://localhost:3000/jiras';

  constructor(protected httpClient: HttpClient) {
  }

  public getByJiraVersion(jira_version: string): Observable<Array<JiraModel>> {
    return this.httpClient.get<Array<JiraModel>>(`${this.API_URL}?jira_version=${jira_version}`)
      .pipe(catchError((res: HttpErrorResponse) => {
        alert(res.statusText);
        return throwError(res.message);
      }));
  }
}
