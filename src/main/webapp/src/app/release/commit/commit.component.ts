import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {ActivatedRoute} from '@angular/router';
import {CommitModel} from './commit.model';
import {CommitsService} from './commits.service';

@Component({
  selector: 'app-commit',
  templateUrl: './commit.component.html',
  styleUrls: ['./commit.component.css']
})
export class CommitComponent implements OnInit {
  tableColumns = ['revision', 'author', 'date', 'message', 'artifact', 'svn_path', 'artf_calc', 'release', 'type'];
  tableSource: MatTableDataSource<CommitModel> | null;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private route: ActivatedRoute, private commitsService: CommitsService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.commitsService.getByJiraVersion(params['id']).subscribe(res => {
        this.tableSource = new MatTableDataSource(res);
        this.tableSource.paginator = this.paginator;
        this.tableSource.sort = this.sort;
      });
    });
  }

}
