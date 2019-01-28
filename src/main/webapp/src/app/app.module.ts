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
import {MainComponent} from './main/main.component';
import {AddDialogComponent} from './main/dialogs/add/add.dialog.component';
import {EditDialogComponent} from './main/dialogs/edit/edit.dialog.component';
import {DeleteDialogComponent} from './main/dialogs/delete/delete.dialog.component';
import {AdminComponent} from './admin/admin.component';
import {AddRepoDialogComponent} from './admin/addRepo/add-repo.dialog.component';
import {EditRepoDialogComponent} from './admin/editRepo/edit-repo.dialog.component';
import {ReportsService} from './services/reports.service';
import {CredentialsService} from './services/credentials.service';
import {RepositoriesService} from './services/repositories.service';
import {ActionsService} from './services/actions.service';
import {ReleaseService} from "./services/release.service";
import {JustifyDialogComponent} from "./release/justify/justify.dialog.component";

const routes: Routes = [
    {path: '', component: MainComponent},
    {path: 'releases/:id', component: ReleaseComponent},
    {path: 'admin', component: AdminComponent},
];

@NgModule({
    declarations: [
        AppComponent,
        MainComponent,
        ReleaseComponent,
        MainComponent,
        AdminComponent,
        AddDialogComponent,
        EditDialogComponent,
        DeleteDialogComponent,
        AddRepoDialogComponent,
        EditRepoDialogComponent,
        JustifyDialogComponent
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
    providers: [ReportsService, ReleaseService, RepositoriesService, CredentialsService, ActionsService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
