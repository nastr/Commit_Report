import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {CredentialModel} from './credential.model';

@Injectable()
export class CredentialsService {
  private readonly URL = 'http://localhost:3000/credentials';

  constructor(protected httpClient: HttpClient) {
  }

  public create(model: CredentialModel): Observable<CredentialModel> {
    return this.httpClient.post<CredentialModel>(this.URL, model);
  }

  public delete(model: CredentialModel): Observable<CredentialModel> {
    return this.httpClient.delete<CredentialModel>(`${this.URL}/${model.name}`);
  }

  public get(name: number): Observable<CredentialModel> {
    return this.httpClient.get<CredentialModel>(`${this.URL}/${name}`)
      .pipe(catchError((res: HttpErrorResponse) => {
        alert(res.statusText);
        return throwError(res.message);
      }));
  }

  public list(): Observable<Array<CredentialModel>> {
    return this.httpClient.get<Array<CredentialModel>>(this.URL);
  }

  public update(model: CredentialModel): Observable<CredentialModel> {
    return this.httpClient.put<CredentialModel>(`${this.URL}/${model.id}`, model);
  }
}
