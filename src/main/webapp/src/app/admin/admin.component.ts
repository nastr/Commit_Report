import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {AddRepoDialogComponent} from './addRepo/add-repo.dialog.component';
import {EditRepoDialogComponent} from './editRepo/edit-repo.dialog.component';
import {RepositoryModel} from '../models/repository.model';
import {CredentialModel} from '../models/credential.model';
import {RepositoriesService} from '../services/repositories.service';
import {CredentialsService} from '../services/credentials.service';

@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
    repos: Array<RepositoryModel> | null;
    credentials: Array<CredentialModel> | null;

    constructor(private repositoryService: RepositoriesService,
                private credentialsService: CredentialsService,
                private dialog: MatDialog) {
    }

    refreshRepos() {
        this.repositoryService.list().subscribe(res => {
            this.repos = res;
        });
    }

    ngOnInit() {
        this.refreshRepos();
        this.credentialsService.list().subscribe(res => {
            this.credentials = res;
        });
    }

    saveProperty(cred: CredentialModel, input: string) {
        cred.value = input;
        this.credentialsService.update(cred).subscribe();
        this.credentials[cred.id].value = input;
    }

    editRepo(id: number, name: string, type: string) {
        const dialogRef = this.dialog.open(EditRepoDialogComponent, {
            data: {id: id, name: name, type: type}
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result === 1) {
                this.refreshRepos();
            }
        });
    }

    deleteRepo(repo: RepositoryModel) {
        if (confirm('Are you sure?')) {
            this.repositoryService.delete(repo).subscribe();
            this.repos.splice(this.repos.indexOf(repo), 1);
        }
    }

    addRepo(repo: RepositoryModel) {
        const dialogRef = this.dialog.open(AddRepoDialogComponent, {
            data: {repo: repo}
        });
        dialogRef.afterClosed().subscribe(result => {
                if (result === 1) {
                    this.refreshRepos();
                }
            }
        );
    }

}
