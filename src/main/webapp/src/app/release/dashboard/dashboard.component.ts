import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {DashboardTableModel} from './models/dashboardTable.model';
import {DashboardTableService} from './services/dashboardTable.service';
import {DashboardSummaryModel} from './models/dashboardSummary.module';
import {DashboardSummaryService} from './services/dashboardSummary.service';
import {ActivatedRoute} from '@angular/router';
import {GoogleChartComponent} from 'ng2-google-charts';
import {JustifyDialogComponent} from './justify/justify.dialog.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  tableSource: MatTableDataSource<DashboardTableModel> | null;
  chartData;
  summary: DashboardSummaryModel = {
    'in_scope': null, 'not_in_scope': null, 'planned': null,
    'justified': null, 'no_jira': null, 'invalid_jira': null
  };
  tableColumns = ['actions', 'commits_revision', 'repository_name', 'commits_message', 'commits_authors',
    'jira_artifact', 'jira_title', 'jira_parent_artifact', 'jira_type', 'scope', 'jira_version'];
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('chart') chart: GoogleChartComponent;

  constructor(private route: ActivatedRoute,
              private dialog: MatDialog,
              private tableService: DashboardTableService,
              private summaryService: DashboardSummaryService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.tableService.getByJiraVersion(params['id']).subscribe(res => {
        this.tableSource = new MatTableDataSource(res);
        this.tableSource.paginator = this.paginator;
        this.tableSource.sort = this.sort;
      });

      this.summaryService.getByJiraVersion(params['id']).subscribe(res => {
        this.summary = res[0];
        // console.log(this.summary);
        this.chartData = {
          chartType: 'PieChart',
          dataTable: [
            ['Task', 'Artifacts status'],
            ['In scope', this.summary.in_scope],
            ['Not in scope', this.summary.not_in_scope],
            ['Planned', this.summary.planned],
            ['Justified', this.summary.justified],
            ['No JIRA', this.summary.no_jira],
            ['Invalid JIRA', this.summary.invalid_jira]
          ],
          options: {
            // width: 600,
            // height: 400,
            is3D: true
          },
        };
      });
    });
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize(event: any) {
    this.chart.redraw();
  }

  justify(i: number, commits_revision: string, repository_name: string, jira_artifact: string,
          jira_title: string, jira_version: string) {
    const dialogRef = this.dialog.open(JustifyDialogComponent, {
      data: {
        commits_revision: commits_revision, repository_name: repository_name, jira_artifact: jira_artifact,
        jira_title: jira_title, jira_version: jira_version
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        const row = this.tableSource.data.filter(x => x.commits_revision === commits_revision
          && x.repository_name === repository_name);
        const foundIndex = this.tableSource.data.findIndex(y => Object.is(row, y));
        this.tableSource.data.splice(foundIndex - 1, 1);
        this.tableSource = new MatTableDataSource<DashboardTableModel>(this.tableSource.data);
      }
    });
  }
}
