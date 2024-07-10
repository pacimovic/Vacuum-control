import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ScheduleDate, Vacuum } from 'src/app/model';
import { AppService } from 'src/app/services/app.service';
import { VacuumService } from 'src/app/services/vacuum.service';

@Component({
  selector: 'app-single-vacuum',
  templateUrl: './single-vacuum.component.html',
  styleUrls: ['./single-vacuum.component.css']
})
export class SingleVacuumComponent implements OnInit{

  router = inject(Router)

  seconds: number[] = []
  minutes: number[] = []
  hours: number[] = []
  daysOfMonth: number[] = []
  months: number[] = []
  daysOfWeek: number[] = []

  selectedSecond: number | string = '*'
  selectedMinute: number | string = '*'
  selectedHour: number | string = '*'
  selectedDayOfMonth: number | string = '*'
  selectedMonth: number | string = '*'
  selectedDayOfWeek: number | string = '*'

  operation: string = 'start'

  vacuum: Vacuum = {
    id: 0,
    name: '',
    status: '',
    created: '',
    active: false
  }

  constructor(private route: ActivatedRoute, private vacuumService: VacuumService, public appService: AppService) {
    for (let i = 0; i <= 59; i++) this.seconds.push(i);
    for (let i = 0; i <= 59; i++) this.minutes.push(i);
    for (let i = 0; i <= 23; i++) this.hours.push(i);
    for (let i = 1; i <= 31; i++) this.daysOfMonth.push(i);
    for (let i = 1; i <= 12; i++) this.months.push(i);
    for (let i = 1; i <= 7; i++) this.daysOfWeek.push(i);
  }

  ngOnInit(): void {
    const id: number = parseInt(<string> this.route.snapshot.paramMap.get('id'))
    this.vacuumService.findVacuum(id).subscribe( vacuum => {
      vacuum.created = vacuum.created.replace("T", " ")
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

  scheduleVacuum(): void {
    if(this.operation === 'start' && !this.appService.permission.can_start_vacuum){
      alert("You don't have permission to schedule START on vacuum cleaner!")
      return
    }
    else if(this.operation === 'stop' && !this.appService.permission.can_stop_vacuum){
      alert("You don't have permission to schedule STOP on vacuum cleaner!")
      return
    }
    else if(this.operation === 'discharge' && !this.appService.permission.can_discharge_vacuum){
      alert("You don't have permission to schedule DISCHARGE on vacuum cleaner!")
      return
    }

    var scheduleDate: ScheduleDate = {
      second: this.selectedSecond.toString(),
      minute: this.selectedMinute.toString(),
      hour: this.selectedHour.toString(),
      dayMonth: this.selectedDayOfMonth.toString(),
      month: this.selectedMonth.toString(),
      dayWeek: this.selectedDayOfWeek.toString()
    }

    this.vacuumService.scheduleOperation(scheduleDate, this.operation, this.vacuum.id).subscribe((vacuum) => {
      alert(this.operation.toUpperCase() + " operation is scheduled on " + this.vacuum.name)
      this.router.navigate(['searchVacuums'])
    })
  }
  
  deleteVacuum(): void {
    this.vacuumService.deleteVacuum(this.vacuum.id).subscribe((vacuum) => {
      alert("Vacuum cleaner: " + this.vacuum.name + " is removed!")
      this.router.navigate(['searchVacuums'])
    })
  }

}
