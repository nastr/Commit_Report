import {Component, OnInit} from '@angular/core';
import {RepositoryModel} from './repository.model';
import {MatDialog} from '@angular/material';
import {CredentialModel} from './credential.model';
import {RepositoriesService} from './repositories.service';
import {CredentialsService} from './credentials.service';
import {AddRepoDialogComponent} from './addRepo/add-repo.dialog.component';
import {EditRepoDialogComponent} from './editRepo/edit-repo.dialog.component';

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

  addRepo(model: RepositoryModel) {
    const dialogRef = this.dialog.open(AddRepoDialogComponent, {
      data: {id: model.id, name: model.name, type: model.type}
    });
    dialogRef.afterClosed().subscribe(result => {
        if (result === 1) {
          this.refreshRepos();
        }
      }
    );
  }

}
