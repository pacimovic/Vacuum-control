import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { NewVacuum } from 'src/app/model';
import { VacuumService } from 'src/app/services/vacuum.service';

@Component({
  selector: 'app-create-vacuum',
  templateUrl: './create-vacuum.component.html',
  styleUrls: ['./create-vacuum.component.css']
})
export class CreateVacuumComponent {

  router = inject(Router)

  constructor(private vacuumService: VacuumService) {}

  vacuum: NewVacuum = {
    name: ''
  }

  createVacuum(): void {
    this.vacuumService.createVacuum(this.vacuum).subscribe(vacuum => {
        alert('Vacuum Created!')
        this.router.navigate(['searchVacuums'])
    })
  }
}
