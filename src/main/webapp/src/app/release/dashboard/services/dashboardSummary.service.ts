import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {DashboardSummaryModel} from '../models/dashboardSummary.module';

@Injectable()
export class DashboardSummaryService {
  private readonly API_URL = 'http://localhost:3000/dashboardSummary';

  constructor(protected httpClient: HttpClient) {
  }

  public getByJiraVersion(jira_version: string): Observable<Array<DashboardSummaryModel>> {
    return this.httpClient.get<Array<DashboardSummaryModel>>(`${this.API_URL}?jira_version=${jira_version}`)
      .pipe(catchError((res: HttpErrorResponse) => {
        alert(res.statusText);
        return throwError(res.message);
      }));
  }
}
