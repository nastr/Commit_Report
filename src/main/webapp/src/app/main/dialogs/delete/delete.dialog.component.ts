import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject} from '@angular/core';
import {ReportsService} from '../../../services/reports.service';
import {ReleaseModel} from "../../../models/release.model";


@Component({
    selector: 'app-delete.dialog',
    templateUrl: './delete.dialog.html',
    styleUrls: ['./delete.dialog.css']
})
export class DeleteDialogComponent {

    constructor(public dialogRef: MatDialogRef<DeleteDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: ReleaseModel,
                public dataService: ReportsService) {
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    confirmDelete(): void {
        this.dataService.delete(this.data.jira_version);
    }
}
