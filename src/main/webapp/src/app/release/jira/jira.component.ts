import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {ActivatedRoute} from '@angular/router';
import {JirasService} from './jiras.service';
import {JiraModel} from './jira.model';

@Component({
  selector: 'app-jira',
  templateUrl: './jira.component.html',
  styleUrls: ['./jira.component.css']
})
export class JiraComponent implements OnInit {
  tableColumns = ['key', 'title', 'type', 'status', 'assignee', 'reporter',
    'parent', 'sub_task', 'affects_versions', 'fix_versions', 'sprint'];
  tableSource: MatTableDataSource<JiraModel> | null;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private route: ActivatedRoute, private jirasService: JirasService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.jirasService.getByJiraVersion(params['id']).subscribe(res => {
        this.tableSource = new MatTableDataSource(res);
        this.tableSource.paginator = this.paginator;
        this.tableSource.sort = this.sort;
      });
    });
  }

}
