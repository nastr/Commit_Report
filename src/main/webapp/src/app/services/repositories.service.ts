import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {catchError} from 'rxjs/operators';
import {RepositoryModel} from '../models/repository.model';

@Injectable()
export class RepositoriesService {
    // private readonly URL = 'http://localhost:3000/repos';
    private readonly URL = 'http://localhost:8080/repositories';

    // private readonly URL = '/repos';

    constructor(protected httpClient: HttpClient) {
    }

    public list(): Observable<Array<RepositoryModel>> {
        return this.httpClient.get<Array<RepositoryModel>>(this.URL);
    }

    public create(model: RepositoryModel): Observable<RepositoryModel> {
        return this.httpClient.post<RepositoryModel>(this.URL, model);
    }

    public delete(model: RepositoryModel): Observable<RepositoryModel> {
        return this.httpClient.delete<RepositoryModel>(`${this.URL}/${model.name}`);
    }

    public get(name: string): Observable<RepositoryModel> {
        return this.httpClient.get<RepositoryModel>(`${this.URL}/${name}`);
        /*.pipe(catchError((res: HttpErrorResponse) => {
          alert(res.statusText);
          return throwError(res.message);
        }));*/
    }

    public update(model: RepositoryModel): Observable<RepositoryModel> {
        return this.httpClient.put<RepositoryModel>(`${this.URL}/${model.name}`, model);
    }
}
