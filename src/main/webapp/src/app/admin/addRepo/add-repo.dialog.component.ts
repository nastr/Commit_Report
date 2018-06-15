import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject,} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {RepositoryModel} from '../repository.model';
import {RepositoriesService} from '../repositories.service';

@Component({
  selector: 'app-add-repo.dialog',
  templateUrl: './add-repo.dialog.html',
  styleUrls: ['./add-repo.dialog.css']
})

export class AddRepoDialogComponent {
  repoType = [
    {value: 'git', viewValue: 'GIT'},
    {value: 'svn', viewValue: 'SVN'}
  ];
  selectedValue: string;

  constructor(public dialogRef: MatDialogRef<AddRepoDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: RepositoryModel,
              public dataService: RepositoriesService) {
  }

  formControl = new FormControl('', [
    Validators.required
  ]);

  getErrorMessage() {
    return this.formControl.hasError('required') ? 'Required field' :
      this.formControl.hasError('email') ? 'Not a valid email' :
        '';
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  public confirmAdd(): void {
    // console.log(this.repoType[this.selectedValue].value);
    this.data.type = this.repoType[this.selectedValue].value;
    this.dataService.create(this.data).subscribe();
  }
}
