import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit{

  router = inject(Router)

  constructor(private route: ActivatedRoute, private userService: UserService) {}

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

  formPassword : string = ''

  ngOnInit(): void {
    const id: number = parseInt(<string>this.route.snapshot.paramMap.get('id'))
    this.userService.findUser(id).subscribe( user => {
      this.user = user
    })
  }

  updateUser(): void {
    if(this.formPassword != '') this.user.password = this.formPassword
    this.userService.updateUser(this.user).subscribe(user => {
      alert('User: ' + user.name + ' is updated!')
      this.router.navigate(['showUsers'])
    })
  }
}
