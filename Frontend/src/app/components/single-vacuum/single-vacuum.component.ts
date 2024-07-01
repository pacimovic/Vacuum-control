import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Vacuum } from 'src/app/model';
import { AppService } from 'src/app/services/app.service';
import { VacuumService } from 'src/app/services/vacuum.service';

@Component({
  selector: 'app-single-vacuum',
  templateUrl: './single-vacuum.component.html',
  styleUrls: ['./single-vacuum.component.css']
})
export class SingleVacuumComponent implements OnInit{

  router = inject(Router)

  constructor(private route: ActivatedRoute, private vacuumService: VacuumService, 
    public appService: AppService
  ) {}

  vacuum: Vacuum = {
    id: 0,
    name: '',
    status: '',
    created: '',
    active: false
  }


  ngOnInit(): void {
    const id: number = parseInt(<string> this.route.snapshot.paramMap.get('id'))
    this.vacuumService.findVacuum(id).subscribe( vacuum => {
      this.vacuum = vacuum
    })
  }

  startVacuum(): void {
    this.vacuumService.startVacuum(this.vacuum).subscribe((vacuum) => {
      alert("Vacuum cleaner is starting...")
    })
  }

  stopVacuum(): void {
    this.vacuumService.stopVacuum(this.vacuum).subscribe((vacuum) => {
      alert("Vacuum cleaner is stoping...")
    })
  }

  dischargeVacuum(): void {
    this.vacuumService.dischargeVacuum(this.vacuum).subscribe((vacuum) => {
      alert("Vacuum cleaner is discharging...")
    })
  }

  
  deleteVacuum(): void {
    this.vacuumService.deleteVacuum(this.vacuum.id).subscribe((vacuum) => {
      alert("Vacuum cleaner: " + this.vacuum.name + " is removed!")
      this.router.navigate(['searchVacuums'])
    })
  }

}
