import { Component, OnInit } from '@angular/core';
import { Vacuum } from 'src/app/model';
import { VacuumService } from 'src/app/services/vacuum.service';

@Component({
  selector: 'app-search-vacuums',
  templateUrl: './search-vacuums.component.html',
  styleUrls: ['./search-vacuums.component.css']
})
export class SearchVacuumsComponent implements OnInit{

  vacuums: Vacuum[] = []

  name: string = ''
  statuses: string[] = []
  running: boolean = false
  stopped: boolean = false
  discharging: boolean = false
  dateFrom: string = ''
  dateTo: string = ''


  constructor(private vacuumService: VacuumService){}


  ngOnInit(): void {
    this.vacuumService.getVacuums('', [''], '', '').subscribe( vacuumsRes => {
      this.vacuums = vacuumsRes
      console.log(this.vacuums)
    })
  }

  submitForm(): void {
    console.log('name: ' + this.name)
    console.log('running:' + this.running)
    console.log('stopped:' + this.stopped)
    console.log('discharging:' + this.discharging)
    console.log('dateFrom:' + this.dateFrom)
    console.log('dateTo: ' + this.dateTo)

    this.name = ''
    this.running = false
    this.stopped = false
    this.discharging = false
    this.dateFrom = ''
    this.dateTo = ''
  }


}
