import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {DashboardTableModel} from '../models/dashboardTable.model';

@Injectable()
export class DashboardTableService {
  private readonly API_URL = 'http://localhost:3000/dashboardTable';

  constructor(protected httpClient: HttpClient) {
  }

  public getByJiraVersion(jira_version: string): Observable<Array<DashboardTableModel>> {
    return this.httpClient.get<Array<DashboardTableModel>>(`${this.API_URL}?jira_version=${jira_version}`)
      .pipe(catchError((res: HttpErrorResponse) => {
        alert(res.statusText);
        return throwError(res.message);
      }));
  }
}
