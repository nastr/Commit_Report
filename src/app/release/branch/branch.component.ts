import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {ActivatedRoute} from '@angular/router';
import {BranchesService} from './branches.service';
import {BranchModel} from './branch.model';

@Component({
  selector: 'app-branch',
  templateUrl: './branch.component.html',
  styleUrls: ['./branch.component.css']
})
export class BranchComponent implements OnInit {
  public currentAction = true;
  tableColumns = ['repository_name', 'path', 'trusted_base_line', 'current_base_line'];
  tableSource: MatTableDataSource<BranchModel> | null;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private route: ActivatedRoute, private branchesService: BranchesService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.branchesService.getByJiraVersion(params['id']).subscribe(res => {
        this.tableSource = new MatTableDataSource(res);
        this.tableSource.paginator = this.paginator;
        this.tableSource.sort = this.sort;
      });
    });
  }

}
