import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ReleasesService} from '../main/releases.service';
import {CorrectionComponent} from './correction/correction.component';
import {MatTabChangeEvent} from '@angular/material';

@Component({
  selector: 'app-row',
  templateUrl: './release.component.html',
  styleUrls: ['./release.component.css']
})
export class ReleaseComponent implements OnInit {
  public isGenerated = false;
  public jira_version = '';

  @ViewChild(CorrectionComponent) private pollComponent: CorrectionComponent;

  // public currentRelease: Release = {'jira_version': '', 'status': '', 'description': '', 'updated': ''};

  constructor(private route: ActivatedRoute, private releasesService: ReleasesService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.releasesService.get(params['id']).subscribe(response => {
        this.isGenerated = response[0].status.toUpperCase() === 'COMPLETE';
        this.jira_version = response[0].jira_version;
        // console.log(response[0]);
        // console.log(this.isGenerated);
      });
    });
  }

  onTabChanged(event: MatTabChangeEvent) {
    if (event.tab.textLabel === 'Correction') {
      this.pollComponent.refresh();
    }
  }

}
