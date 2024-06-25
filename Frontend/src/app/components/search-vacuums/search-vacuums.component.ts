import { Component, OnInit } from '@angular/core';
import { Vacuum } from 'src/app/model';
import { VacuumService } from 'src/app/services/vacuum.service';
import { parse, isValid, format } from 'date-fns';

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
    })
  }

  submitForm(): void {
    if(this.dateFrom != '' && !this.isDateInFormat(this.dateFrom,"yyyy-MM-dd")){
      alert('Date is not valid!')
    }
    else if(this.dateTo != '' && !this.isDateInFormat(this.dateTo,"yyyy-MM-dd")){
      alert('Date is not valid!')
    }
    else{
      if(this.running) this.statuses.push('RUNNING')
      if(this.stopped) this.statuses.push('STOPPED')
      if(this.discharging) this.statuses.push('DISCHARGING')
      if(this.statuses.length == 0) this.statuses = ['']

      this.vacuumService.getVacuums(this.name, this.statuses, this.dateFrom, this.dateTo).subscribe( vacuumsRes => {
        this.vacuums = vacuumsRes
      })
    
    } 


    this.name = ''
    this.running = false
    this.stopped = false
    this.discharging = false
    this.dateFrom = ''
    this.dateTo = ''
    this.statuses = []
  }



  isDateInFormat(dateStr: string, formatStr: string): boolean {
    const parsedDate = parse(dateStr, formatStr, new Date());
    return isValid(parsedDate) && format(parsedDate, formatStr) === dateStr;
  }
}


