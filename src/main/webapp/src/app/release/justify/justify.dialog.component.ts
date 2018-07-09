import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject, EventEmitter, OnInit} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {ReleaseService} from "../../services/release.service";
import {CorrectionRequestModel} from "../../models/correction-request.model";

@Component({
    selector: 'app-justify.dialog',
    templateUrl: './justify.dialog.html',
    styleUrls: ['./justify.dialog.css']
})

export class JustifyDialogComponent implements OnInit {

    public _justification: string = "";
    public onAdd = new EventEmitter();

    // public correctionRow: CorrectionRequestModel | null;

    constructor(public dialogRef: MatDialogRef<JustifyDialogComponent>,
                // @Inject(MAT_DIALOG_DATA) public correction: CorrectionModel,
                @Inject(MAT_DIALOG_DATA) public correctionRow: CorrectionRequestModel,
                public service: ReleaseService) {
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
        this.correctionRow.justification = this._justification;
        // console.log(this.correctionRow);
        this.onAdd.emit(this._justification);
        this.service.create(this.correctionRow);
    }

    ngOnInit(): void {
        this.correctionRow = this.correctionRow['correctionRow'];
        // console.log(this.correctionRow);
    }
}
