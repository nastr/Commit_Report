import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {BranchModel} from './branch.model';

@Injectable()
export class BranchesService {
  private readonly API_URL = 'http://localhost:3000/branches';

  constructor(protected httpClient: HttpClient) {
  }

  public create(corrections: BranchModel): Observable<BranchModel> {
    return this.httpClient.post<BranchModel>(this.API_URL, corrections);
  }

  public getByJiraVersion(jira_version: string): Observable<Array<BranchModel>> {
    return this.httpClient.get<Array<BranchModel>>(`${this.API_URL}?jira_version=${jira_version}`)
      .pipe(catchError((res: HttpErrorResponse) => {
        alert(res.statusText);
        return throwError(res.message);
      }));
  }
}
