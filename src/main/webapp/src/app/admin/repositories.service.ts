import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {RepositoryModel} from './repository.model';

@Injectable()
export class RepositoriesService {
  private readonly URL = 'http://localhost:3000/repos';

  constructor(protected httpClient: HttpClient) {
  }

  public create(model: RepositoryModel): Observable<RepositoryModel> {
    return this.httpClient.post<RepositoryModel>(this.URL, model);
  }

  public delete(model: RepositoryModel): Observable<RepositoryModel> {
    return this.httpClient.delete<RepositoryModel>(`${this.URL}/${model.id}`);
  }

  public get(id: number): Observable<RepositoryModel> {
    return this.httpClient.get<RepositoryModel>(`${this.URL}/${id}`)
      .pipe(catchError((res: HttpErrorResponse) => {
        alert(res.statusText);
        return throwError(res.message);
      }));
  }

  public list(): Observable<Array<RepositoryModel>> {
    return this.httpClient.get<Array<RepositoryModel>>(this.URL);
  }

  public update(model: RepositoryModel): Observable<RepositoryModel> {
    return this.httpClient.put<RepositoryModel>(`${this.URL}/${model.id}`, model);
  }
}
