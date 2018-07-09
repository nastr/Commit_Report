import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {ReportsService} from '../../../services/reports.service';

@Component({
    selector: 'app-baza.dialog',
    templateUrl: './edit.dialog.html',
    styleUrls: ['./edit.dialog.css']
})
export class EditDialogComponent {

    constructor(public dialogRef: MatDialogRef<EditDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any,
                public dataService: ReportsService) {
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
        this.dataService.update(this.data);
    }
}
