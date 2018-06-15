import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject} from '@angular/core';
import {ReleasesService} from '../../releases.service';


@Component({
  selector: 'app-delete.dialog',
  templateUrl: './delete.dialog.html',
  styleUrls: ['./delete.dialog.css']
})
export class DeleteDialogComponent {

  constructor(public dialogRef: MatDialogRef<DeleteDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dataService: ReleasesService) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  confirmDelete(): void {
    this.dataService.delete(this.data.id);
  }
}
