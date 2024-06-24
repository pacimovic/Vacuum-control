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

  constructor(private vacuumService: VacuumService){}


  ngOnInit(): void {
    this.vacuumService.getVacuums('', [''], '', '').subscribe( vacuumsRes => {
      this.vacuums = vacuumsRes
      console.log(this.vacuums)
    })
  }


}
