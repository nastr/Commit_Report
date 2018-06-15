import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {ActivatedRoute} from '@angular/router';
import {CorrectionsService} from './corrections.service';
import {CorrectionModel} from './correction.model';

@Component({
  selector: 'app-correction',
  templateUrl: './correction.component.html',
  styleUrls: ['./correction.component.css']
})
export class CorrectionComponent implements OnInit {
  tableColumns = ['commits_revision', 'repository_name', 'jira_artifact',
    'jira_title', 'justification', 'status', 'date', 'author', 'jira_version'];
  tableSource: MatTableDataSource<CorrectionModel> | null;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private route: ActivatedRoute, private correctionsService: CorrectionsService) {
  }

  refresh() {
    this.route.params.subscribe(params => {
      this.correctionsService.getByJiraVersion(params['id']).subscribe(res => {
        this.tableSource = new MatTableDataSource(res);
        this.tableSource.paginator = this.paginator;
        this.tableSource.sort = this.sort;
      });
    });
  }

  ngOnInit() {
    this.refresh();
  }
}
