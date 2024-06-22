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

  user: User = {
    userId: 0,
    name: '',
    surname: '',
    email: '',
    password: '',
    permission: {
        can_create_users: false,
        can_read_users: false,
        can_update_users: false,
        can_delete_users: false
    }
  }

  constructor(private route: ActivatedRoute, private userService: UserService) {}

  ngOnInit(): void {
    const id: number = parseInt(<string>this.route.snapshot.paramMap.get('id'))
    this.userService.findUser(id).subscribe( user => {
      this.user = user
      this.user.password = ''
    })
  }

  updateUser(): void {
    this.userService.updateUser(this.user).subscribe(user => {
      alert('User: ' + user.name + ' is updated!')
      this.router.navigate(['showUsers'])
    })
  }
}
