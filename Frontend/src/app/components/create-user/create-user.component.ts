import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent {

  router = inject(Router)

  constructor(private userService: UserService) {}


  user: User = {
    id: 0,
    name: '',
    surname: '',
    email: '',
    password: '',
    permission: {
        can_create_users: false,
        can_read_users: false,
        can_update_users: false,
        can_delete_users: false,
        can_search_vacuum: false,
        can_start_vacuum: false,
        can_stop_vacuum: false,
        can_discharge_vacuum: false,
        can_add_vacuum: false,
        can_remove_vacuum: false
    }
  }


  createUser(): void {
    this.userService.createUser(this.user).subscribe(user => {
      console.log(user)
      alert('User Created!')
      this.router.navigate(['showUsers'])
    })
  }
  

}
