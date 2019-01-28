import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MatDialog, MatPaginator, MatSort, MatTabChangeEvent, MatTableDataSource} from '@angular/material';
import {ReportsService} from '../services/reports.service';
import {ActionsService} from '../services/actions.service';
import {BranchModel} from "../models/branch.model";
import {ReleaseService} from "../services/release.service";
import {CommitModel} from "../models/commit.model";
import {CorrectionModel} from "../models/correction.model";
import {DashboardSummaryModel} from "../models/dashboardSummary.model";
import {DashboardTableModel} from "../models/dashboardTable.model";
import {JiraModel} from "../models/jira.model";
import {GoogleChartComponent} from "ng2-google-charts";
import {JustifyDialogComponent} from "./justify/justify.dialog.component";

@Component({
    selector: 'app-row',
    templateUrl: './release.component.html',
    styleUrls: ['./release.component.css']
})
export class ReleaseComponent implements OnInit {
    private _isGenerated = false;
    private _jira_version = '';

    private _branchesReposAutoFinding = true;
    private _branchesTableContent: MatTableDataSource<BranchModel> | null;
    private _branchesTableColumns = ['repository_name', 'path', 'trusted_base_line', 'current_base_line'];

    private _commitsTableContent: MatTableDataSource<CommitModel> | null;
    private _commitsTableColumns = ['revision', 'author', 'date', 'message', 'artifact', 'svn_path', 'artf_calc', 'release', 'type'];

    private _correctionsTableContent: MatTableDataSource<CorrectionModel> | null;
    private _correctionsTableColumns = ['commits_revision', 'repository_name', 'justification', 'author', 'date'];

    private _jirasTableContent: MatTableDataSource<JiraModel> | null;
    private _jirasTableColumns = ['key', 'title', 'type', 'status', 'assignee', 'reporter',
        'parent', 'sub_task', 'affects_versions', 'fix_versions', 'sprint'];

    private _dashboardTableContent: MatTableDataSource<DashboardTableModel> | null;
    private _dashboardTableColumns = ['actions', 'commits_revision', 'repository_name', 'commits_message',
        'commits_authors', 'jira_artifact', 'jira_title', 'jira_parent_artifact', 'jira_type', 'scope', 'jira_version'];
    private _dashboardSummary: DashboardSummaryModel = {'in_scope': null, 'not_in_scope': null, 'justified': null};
    private _dashboardChart;

    @ViewChild(MatPaginator) private _paginate: MatPaginator;
    @ViewChild(MatSort) private _sort: MatSort;
    @ViewChild('_chart') private _chart: GoogleChartComponent;

    // @ViewChild(CorrectionComponent) private pollComponent: CorrectionComponent;

    constructor(private route: ActivatedRoute,
                private actionsService: ActionsService,
                private releasesService: ReportsService,
                private releaseService: ReleaseService,
                private dialog: MatDialog) {
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            // console.log(params['id']);
            this.releasesService.get(params['id']).subscribe(response => {
                // console.log(response);
                this._isGenerated = response.status.toUpperCase() === 'COMPLETE';
                this._jira_version = response.jira_version;

                this.releaseService.getData(params['id']).subscribe(res => {
                    // console.log(res);
                    this._branchesTableContent = new MatTableDataSource(res['branches'] as Array<BranchModel>);
                    this._commitsTableContent = new MatTableDataSource(res['commits'] as Array<CommitModel>);
                    this._correctionsTableContent = new MatTableDataSource(res['corrections'] as Array<CorrectionModel>);
                    this._dashboardTableContent = new MatTableDataSource(res['dashboardTable'] as Array<DashboardTableModel>);
                    this._jirasTableContent = new MatTableDataSource(res['jiras'] as Array<JiraModel>);
                    this._branchesTableContent.paginator = this._paginate;
                    this._branchesTableContent.sort = this._sort;
                    this._commitsTableContent.paginator = this._paginate;
                    this._commitsTableContent.sort = this._sort;
                    this._correctionsTableContent.paginator = this._paginate;
                    this._correctionsTableContent.sort = this._sort;
                    this._dashboardTableContent.paginator = this._paginate;
                    this._dashboardTableContent.sort = this._sort;
                    this._jirasTableContent.paginator = this._paginate;
                    this._jirasTableContent.sort = this._sort;
                    this._dashboardSummary = res['dashboardSummary'] as DashboardSummaryModel;
                    // console.log(this._dashboardSummary);
                    this._dashboardChart = {
                        chartType: 'PieChart',
                        dataTable: [
                            ['Task', 'Artifacts status'],
                            ['In scope', this._dashboardSummary.in_scope],
                            ['Not in scope', this._dashboardSummary.not_in_scope],
                            ['Justified', this._dashboardSummary.justified],
                        ],
                        options: {
                            // width: 600,
                            // height: 400,
                            is3D: true
                        },
                    };
                });
            });
        });
    }


    get isGenerated(): boolean {
        return this._isGenerated;
    }

    get jira_version(): string {
        return this._jira_version;
    }

    get branchesReposAutoFinding(): boolean {
        return this._branchesReposAutoFinding;
    }

    get branchesTableContent(): MatTableDataSource<BranchModel> | null {
        return this._branchesTableContent;
    }

    get branchesTableColumns(): string[] {
        return this._branchesTableColumns;
    }

    get commitsTableContent(): MatTableDataSource<CommitModel> | null {
        return this._commitsTableContent;
    }

    get commitsTableColumns(): string[] {
        return this._commitsTableColumns;
    }

    get correctionsTableContent(): MatTableDataSource<CorrectionModel> | null {
        return this._correctionsTableContent;
    }

    get correctionsTableColumns(): string[] {
        return this._correctionsTableColumns;
    }

    get jirasTableContent(): MatTableDataSource<JiraModel> | null {
        return this._jirasTableContent;
    }

    get jirasTableColumns(): string[] {
        return this._jirasTableColumns;
    }

    get dashboardTableContent(): MatTableDataSource<DashboardTableModel> | null {
        return this._dashboardTableContent;
    }

    get dashboardTableColumns(): string[] {
        return this._dashboardTableColumns;
    }

    get dashboardSummary(): DashboardSummaryModel {
        return this._dashboardSummary;
    }

    get dashboardChart() {
        return this._dashboardChart;
    }

    get sort(): MatSort {
        return this._sort;
    }

    get chart(): GoogleChartComponent {
        return this._chart;
    }

    /*onTabChanged(event: MatTabChangeEvent) {
        if (event.tab.textLabel === 'Correction') {
            this.pollComponent.refresh();
        }
    }*/

    public async createAndDownloadBlobFile() {
        const blob = await this.actionsService.downloadResource(this._jira_version);
        // if (navigator.msSaveBlob) {
        //     navigator.msSaveBlob(blob);
        // }
        // else {
        var link = document.createElement("a");
        // Browsers that support HTML5 download attribute
        if (link.download !== undefined) {
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", this._jira_version + "_Commit_report.xlsx");
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
        // }
    }

    public generateReport() {
        this.actionsService.generateReport(this._jira_version);
    }

    /*@HostListener('window:resize', ['$event'])
    public onWindowResize(event: any) {
        this._chart.redraw();
    }*/

    justify(i: number, commit: DashboardTableModel) {
        let correctionRequest: CorrectionModel = {
            repositoryName: commit.repository_name,
            commitsRevision: commit.commits_revision,
            justification: null,
            justificationAuthor: 'current user',
            reportJiraVersion: this._jira_version,
            date: Date.now()
        };
        const dialogRef = this.dialog.open(JustifyDialogComponent, {
            data: {correctionRow: correctionRequest}
        });
        let correctionRow: CorrectionModel = {
            commitsRevision: commit.commits_revision,
            repositoryName: commit.repository_name,
            justification: null,
            justificationAuthor: 'current user',
            reportJiraVersion: commit.jira_version,
            date: Date.now()
        };
        dialogRef.componentInstance.onAdd.subscribe((justification: string) => {
            correctionRow.justification = justification;
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result === 1) {
                let dashboardTableRow: DashboardTableModel = this._dashboardTableContent.data.filter(x => x === commit)[0];
                const foundIndex = this._dashboardTableContent.data.findIndex(y => Object.is(dashboardTableRow, y));
                this._dashboardTableContent.data.splice(foundIndex - 1, 1);
                this._dashboardTableContent = new MatTableDataSource<DashboardTableModel>(this._dashboardTableContent.data);
                this._correctionsTableContent.data.push(correctionRow);
                this._correctionsTableContent = new MatTableDataSource<CorrectionModel>(this._correctionsTableContent.data);
                this._dashboardSummary.justified++;
                this._dashboardSummary.not_in_scope--;

                this._dashboardChart = {
                    chartType: 'PieChart',
                    dataTable: [
                        ['Task', 'Artifacts status'],
                        ['In scope', this._dashboardSummary.in_scope],
                        ['Not in scope', this._dashboardSummary.not_in_scope],
                        ['Justified', this._dashboardSummary.justified],
                    ],
                    options: {
                        // width: 600,
                        // height: 400,
                        is3D: true
                    },
                };
            }
        });
    }
}
