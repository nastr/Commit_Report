import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {CorrectionModel} from './correction.model';

@Injectable()
export class CorrectionsService {
  private readonly API_URL = 'http://localhost:3000/corrections';

  constructor(protected httpClient: HttpClient) {
  }

  public create(corrections: CorrectionModel): Observable<CorrectionModel> {
    return this.httpClient.post<CorrectionModel>(this.API_URL, corrections);
  }

  public getByJiraVersion(jira_version: string): Observable<Array<CorrectionModel>> {
    return this.httpClient.get<Array<CorrectionModel>>(`${this.API_URL}?jira_version=${jira_version}`)
      .pipe(catchError((res: HttpErrorResponse) => {
        alert(res.statusText);
        return throwError(res.message);
      }));
  }
}
