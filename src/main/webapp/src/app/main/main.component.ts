import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {AddDialogComponent} from './dialogs/add/add.dialog.component';
import {EditDialogComponent} from './dialogs/edit/edit.dialog.component';
import {DeleteDialogComponent} from './dialogs/delete/delete.dialog.component';
import {Router} from '@angular/router';
import {ReportsService} from '../services/reports.service';
import {ReleaseModel} from '../models/release.model';
import {ActionsService} from "../services/actions.service";

@Component({
    selector: 'app-releases',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.css']
})

export class MainComponent implements OnInit {
    displayedColumns = ['jira_version', 'status', 'release_name', 'updated', 'actions'];
    tableSource: MatTableDataSource<ReleaseModel> | null;

    constructor(private httpClient: HttpClient,
                private dialog: MatDialog,
                private releasesService: ReportsService,
                private actionsService: ActionsService,
                private router: Router) {
    }

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    // @ViewChild('filter') filter: ElementRef;

    setSelectedRow(row: ReleaseModel) {
        this.router.navigate(['/releases', row.jira_version]);
    }

    ngOnInit() {
        this.loadData();
    }

    addNew(model: ReleaseModel) {
        const dialogRef = this.dialog.open(AddDialogComponent, {
            data: {model: model}
        });
        dialogRef.afterClosed().subscribe(result => {
                if (result === 1) {
                    this.loadData();
                }
            }
        );
    }

    startEdit(i: number, jira_version: string, release_name: string) {
        const dialogRef = this.dialog.open(EditDialogComponent, {
            data: {jira_version: jira_version, release_name: release_name}
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result === 1) {
                this.loadData();
            }
        });
    }

    deleteItem(i: number, jira_version: string, release_name: string, status: string, updated: string) {
        const dialogRef = this.dialog.open(DeleteDialogComponent, {
            data: {jira_version: jira_version, release_name: release_name, status: status, updated: updated}
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result === 1) {
                this.loadData();
            }
        });
    }

    public loadData() {
        this.releasesService.list().subscribe(res => {
            this.tableSource = new MatTableDataSource(res);
            this.tableSource.paginator = this.paginator;
            this.tableSource.sort = this.sort;
        });
    }

    generate() {
        console.log('to be implemented');
    }

    public generateReport(jira_version: string) {
        this.actionsService.generateReport(jira_version);
    }
}
