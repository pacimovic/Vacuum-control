import { Component, OnInit } from '@angular/core';
import { ErrorMessage } from 'src/app/model';
import { ErrorService } from 'src/app/services/error.service';

@Component({
  selector: 'app-show-errors',
  templateUrl: './show-errors.component.html',
  styleUrls: ['./show-errors.component.css']
})
export class ShowErrorsComponent implements OnInit{

  errors: ErrorMessage[] = []

  constructor(private errorService: ErrorService) {}


  ngOnInit(): void {
    this.errorService.getAllErrors().subscribe((errors) => {
      for(let i = 0; i < errors.length; i++){
        errors[i].date = errors[i].date.replace("T"," ")
      }
      this.errors = errors
    })
  }

}
