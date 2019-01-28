import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {RepositoriesService} from '../../services/repositories.service';

@Component({
    selector: 'app-edit-repo.dialog',
    templateUrl: './edit-repo.dialog.html',
    styleUrls: ['./edit-repo.dialog.css']
})
export class EditRepoDialogComponent {
    repoType = [
        {value: 'git', viewValue: 'GIT'},
        {value: 'svn', viewValue: 'SVN'}
    ];
    selectedName: string;
    selectedTypeIndex: number;

    constructor(public dialogRef: MatDialogRef<EditRepoDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any,
                public dataService: RepositoriesService) {
        this.selectedName = this.data.name;
        this.selectedTypeIndex = this.data.type;
        // console.log(this.data.type);
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

    stopEdit(): void {
        this.data.name = this.selectedName;
        this.data.type = this.repoType[this.selectedTypeIndex].value;
        // console.log(this.data);
        this.dataService.update(this.data).subscribe();
    }
}
