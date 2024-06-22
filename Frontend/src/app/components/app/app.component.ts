import { Component, OnInit } from '@angular/core';
import { Permission } from 'src/app/model';
import { AppService } from 'src/app/services/app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Frontend';

  constructor(public appService: AppService) {}

}
