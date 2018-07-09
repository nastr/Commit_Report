import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {ActionsModel} from "../models/actions.model";

@Injectable()
export class ActionsService {
    private readonly API_URL = 'http://localhost:8080/actions';

    // private readonly API_URL = '/actions';

    constructor(private httpClient: HttpClient) {
    }

    public async downloadResource(jira_version: string): Promise<Blob> {
        const file = await this.httpClient.get<Blob>(
            this.API_URL + "/" + jira_version,
            {responseType: 'blob' as 'json'}).toPromise();
        // console.log("getting file")
        return file;
    }

    public generateReport(jira_version: string): void {
        let obj: ActionsModel = {
            'jira_version': jira_version,
            'action': 'generate'
        };
        // console.log(this.API_URL + '/', obj);
        this.httpClient.post(this.API_URL + '/', obj).subscribe();
    }
}
