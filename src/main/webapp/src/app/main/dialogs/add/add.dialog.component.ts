import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {ReleaseModel} from '../../../models/release.model';
import {ReportsService} from '../../../services/reports.service';

@Component({
    selector: 'app-add.dialog',
    templateUrl: './add.dialog.html',
    styleUrls: ['./add.dialog.css']
})

export class AddDialogComponent {
    constructor(public dialogRef: MatDialogRef<AddDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: ReleaseModel,
                public dataService: ReportsService) {
    }

    formControl = new FormControl('', [
        Validators.required
        // Validators.email,
    ]);

    getErrorMessage() {
        return this.formControl.hasError('required') ? 'Required field' :
            this.formControl.hasError('email') ? 'Not a valid email' :
                '';
    }

    submit() {
        // emppty stuff
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    public confirmAdd(): void {
        if (!this.data.status) {
            this.data.status = "DRAFT";
        }
        if (!this.data.updated) {
            this.data.updated = Date.now();
        }
        this.dataService.add(this.data);
    }
}
