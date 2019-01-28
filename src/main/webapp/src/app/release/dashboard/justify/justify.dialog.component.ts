import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {CorrectionsService} from '../../correction/corrections.service';
import {DashboardTableModel} from '../models/dashboardTable.model';
import {CorrectionModel} from '../../correction/correction.model';

@Component({
  selector: 'app-justify.dialog',
  templateUrl: './justify.dialog.html',
  styleUrls: ['./justify.dialog.css']
})

export class JustifyDialogComponent {
  constructor(public dialogRef: MatDialogRef<JustifyDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public correction: CorrectionModel,
              @Inject(MAT_DIALOG_DATA) public table: DashboardTableModel,
              public dataService: CorrectionsService) {
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

  onNoClick(): void {
    this.dialogRef.close();
  }

  public confirmJustification(): void {
    this.correction.commits_revision = this.table.commits_revision;
    this.correction.repository_name = this.table.repository_name;
    this.correction.jira_artifact = this.table.jira_artifact;
    this.correction.jira_title = this.table.jira_title;
    this.correction.jira_version = this.table.jira_version;
    this.correction.date = Date.now();
    this.correction.status = 'Green';
    // to provide
    this.correction.author = '';
    this.dataService.create(this.correction).subscribe();
  }
}
