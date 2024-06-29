import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Vacuum } from 'src/app/model';
import { VacuumService } from 'src/app/services/vacuum.service';

@Component({
  selector: 'app-single-vacuum',
  templateUrl: './single-vacuum.component.html',
  styleUrls: ['./single-vacuum.component.css']
})
export class SingleVacuumComponent implements OnInit{

  router = inject(Router)

  constructor(private route: ActivatedRoute, private vacuumService: VacuumService) {}

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

}
