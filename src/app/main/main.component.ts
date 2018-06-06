import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatDialog, MatPaginator, MatSort} from '@angular/material';
import {Observable} from 'rxjs';
import {BehaviorSubject} from 'rxjs';
import {merge} from 'rxjs';
import {fromEvent} from 'rxjs';
import {map} from 'rxjs/operators';
import {AddDialogComponent} from './dialogs/add/add.dialog.component';
import {EditDialogComponent} from './dialogs/edit/edit.dialog.component';
import {DeleteDialogComponent} from './dialogs/delete/delete.dialog.component';
import {ReleasesService} from './releases.service';
import {DataSource} from '@angular/cdk/collections';
import {Router} from '@angular/router';
import {ReleaseModel} from './release.model';

@Component({
  selector: 'app-releases',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})

export class MainComponent implements OnInit {
  displayedColumns = ['jira_version', 'status', 'description', 'updated', 'actions'];
  exampleDatabase: ReleasesService | null;
  dataSource: ExampleDataSource | null;

  constructor(private httpClient: HttpClient,
              private dialog: MatDialog,
              private releasesService: ReleasesService,
              private router: Router) {
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild('filter') filter: ElementRef;

  setSelectedRow(row: ReleaseModel) {
    this.router.navigate(['/releases', row.jira_version]);
  }

  ngOnInit() {
    this.loadData();
  }

  refresh() {
    this.loadData();
    // this.selectedRelease = null;
  }

  addNew(model: ReleaseModel) {
    const dialogRef = this.dialog.open(AddDialogComponent, {
      data: {issue: model}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        // After dialog is closed we're doing frontend updates
        // For add we're just pushing a new row inside DataService
        this.exampleDatabase.dataChange.value.push(this.releasesService.getDialogData());
        this.refreshTable();
      }
    });
  }

  startEdit(i: number, jira_version: string, status: string, description: string, updated: string) {
    // this.id = id;
    // index row is used just for debugging proposes and can be removed
    // this.index = i;
    // console.log(this.index);
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: {jira_version: jira_version, status: status, description: description, updated: updated}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        // When using an edit things are little different, firstly we find record inside DataService by id
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.jira_version === jira_version);
        // Then you update that record using data from dialogData (values you enetered)
        this.exampleDatabase.dataChange.value[foundIndex] = this.releasesService.getDialogData();
        // And lastly refresh table
        this.refreshTable();
      }
    });
  }

  deleteItem(i: number, jira_version: string, status: string, description: string) {
    // this.index = i;
    // this.id = id;
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: {jira_version: jira_version, status: status, description: description}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.jira_version === jira_version);
        // for delete we use splice in order to remove single object from DataService
        this.exampleDatabase.dataChange.value.splice(foundIndex, 1);
        this.refreshTable();
      }
    });
  }


  // If you don't need a filter or a pagination this can be simplified, you just use code from else block
  private refreshTable() {
    // if there's a paginator active we're using it for refresh
    if (this.dataSource._paginator.hasNextPage()) {
      this.dataSource._paginator.nextPage();
      this.dataSource._paginator.previousPage();
      // in case we're on last page this if will tick
    } else if (this.dataSource._paginator.hasPreviousPage()) {
      this.dataSource._paginator.previousPage();
      this.dataSource._paginator.nextPage();
      // in all other cases including active filter we do it like this
    } else {
      this.dataSource.filter = '';
      this.dataSource.filter = this.filter.nativeElement.value;
    }
  }

  public loadData() {
    this.exampleDatabase = new ReleasesService(this.httpClient);
    this.dataSource = new ExampleDataSource(this.exampleDatabase, this.paginator, this.sort);
    fromEvent(this.filter.nativeElement, 'keyup')
      .subscribe(() => {
        if (!this.dataSource) {
          return;
        }
        this.dataSource.filter = this.filter.nativeElement.value;
      });
  }
}


export class ExampleDataSource extends DataSource<ReleaseModel> {
  _filterChange = new BehaviorSubject('');

  get filter(): string {
    return this._filterChange.value;
  }

  set filter(filter: string) {
    this._filterChange.next(filter);
  }

  filteredData: ReleaseModel[] = [];
  renderedData: ReleaseModel[] = [];

  constructor(public _exampleDatabase: ReleasesService,
              public _paginator: MatPaginator,
              public _sort: MatSort) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => this._paginator.pageIndex = 0);
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Array<ReleaseModel>> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page
    ];

    this._exampleDatabase.getAll();

    return merge(...displayDataChanges).pipe(map(() => {
      // Filter data
      this.filteredData = this._exampleDatabase.data.slice().filter((release: ReleaseModel) => {
        const searchStr = (release.jira_version + release.status + release.description).toLowerCase();
        return searchStr.indexOf(this.filter.toLowerCase()) !== -1;
      });

      // Sort filtered data
      const sortedData = this.sortData(this.filteredData.slice());

      // Grab the page's slice of the filtered sorted data.
      const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
      this.renderedData = sortedData.splice(startIndex, this._paginator.pageSize);
      return this.renderedData;
    }));
  }

  disconnect() {
  }


  /** Returns a sorted copy of the database data. */
  sortData(data: ReleaseModel[]): ReleaseModel[] {
    if (!this._sort.active || this._sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';

      switch (this._sort.active) {
        case 'release_name':
          [propertyA, propertyB] = [a.jira_version, b.jira_version];
          break;
        case 'status':
          [propertyA, propertyB] = [a.status, b.status];
          break;
        case 'jira_version':
          [propertyA, propertyB] = [a.description, b.description];
          break;
        case 'updated':
          [propertyA, propertyB] = [a.updated, b.updated];
          break;
      }

      const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      const valueB = isNaN(+propertyB) ? propertyB : +propertyB;

      return (valueA < valueB ? -1 : 1) * (this._sort.direction === 'asc' ? 1 : -1);
    });
  }
}
