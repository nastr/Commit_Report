import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {Ng2GoogleChartsModule} from 'ng2-google-charts';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatDialogModule,
  MatInputModule,
  MatPaginatorModule,
  MatSortModule,
  MatIconModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatCardModule,
  MatFormFieldModule,
  MatRadioModule,
  MatListModule,
  MatExpansionModule,
  MatSelectModule,
} from '@angular/material';
import {ReleaseComponent} from './release/release.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './release/dashboard/dashboard.component';
import {DashboardTableService} from './release/dashboard/services/dashboardTable.service';
import {DashboardSummaryService} from './release/dashboard/services/dashboardSummary.service';
import {MainComponent} from './main/main.component';
import {AddDialogComponent} from './main/dialogs/add/add.dialog.component';
import {EditDialogComponent} from './main/dialogs/edit/edit.dialog.component';
import {DeleteDialogComponent} from './main/dialogs/delete/delete.dialog.component';
import {ReleasesService} from './main/releases.service';
import {JustifyDialogComponent} from './release/dashboard/justify/justify.dialog.component';
import {CorrectionsService} from './release/correction/corrections.service';
import {CommitComponent} from './release/commit/commit.component';
import {JiraComponent} from './release/jira/jira.component';
import {BranchComponent} from './release/branch/branch.component';
import {CorrectionComponent} from './release/correction/correction.component';
import {CommitsService} from './release/commit/commits.service';
import {JirasService} from './release/jira/jiras.service';
import {BranchesService} from './release/branch/branches.service';
import {AdminComponent} from './admin/admin.component';
import {CredentialsService} from './admin/credentials.service';
import {RepositoriesService} from './admin/repositories.service';
import {AddRepoDialogComponent} from './admin/addRepo/add-repo.dialog.component';
import {EditRepoDialogComponent} from './admin/editRepo/edit-repo.dialog.component';

const routes: Routes = [
  {path: '', component: MainComponent},
  {path: 'releases/:id', component: ReleaseComponent},
  {path: 'admin', component: AdminComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    AddDialogComponent,
    EditDialogComponent,
    DeleteDialogComponent,
    ReleaseComponent,
    MainComponent,
    DashboardComponent,
    BranchComponent,
    JustifyDialogComponent,
    CorrectionComponent,
    CommitComponent,
    JiraComponent,
    AdminComponent,
    AddRepoDialogComponent,
    EditRepoDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatSortModule,
    MatTableModule,
    MatToolbarModule,
    MatPaginatorModule,
    MatTabsModule,
    MatCardModule,
    MatFormFieldModule,
    MatRadioModule,
    MatListModule,
    MatExpansionModule,
    MatSelectModule,
    ReactiveFormsModule,
    Ng2GoogleChartsModule,
    RouterModule.forRoot(
      routes,
      {enableTracing: false}
    )
  ],
  entryComponents: [
    AddDialogComponent,
    EditDialogComponent,
    DeleteDialogComponent,
    JustifyDialogComponent,
    AddRepoDialogComponent,
    EditRepoDialogComponent
  ],
  providers: [ReleasesService, DashboardTableService, DashboardSummaryService, CorrectionsService,
    CommitsService, JirasService, BranchesService, RepositoriesService, CredentialsService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
