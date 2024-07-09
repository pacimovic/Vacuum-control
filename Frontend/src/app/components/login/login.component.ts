import { Component } from '@angular/core';
import { LoginRequest, Permission } from 'src/app/model';
import { AppService } from 'src/app/services/app.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginRequest: LoginRequest = {
    email: '',
    password: ''
  }

  constructor(private loginService: LoginService, private appService: AppService) { }

  login(): void {
    //console.log('Email: ' + this.loginRequest.email + ' ,Password: ' + this.loginRequest.password)
    this.loginService.login(this.loginRequest).subscribe(loginResponse => {

      //cuvanje jwt-a u local storage
      localStorage.removeItem('jwt')
      localStorage.setItem('jwt', loginResponse.jwt)

      //cuvanje permisija za ulogovanog user-a u local storage
      localStorage.removeItem('permission')
      localStorage.setItem('permission', JSON.stringify(loginResponse.permission))

      //prikaz ili sakrivanje linka za kreiranje korisnika od zavisnosti od permisije
      this.appService.permission = loginResponse.permission

      alert('Login successful')

      var permission: Permission = loginResponse.permission
      if (!permission.can_create_users && !permission.can_read_users && !permission.can_update_users && !permission.can_delete_users 
        && !permission.can_search_vacuum && !permission.can_start_vacuum && !permission.can_stop_vacuum && !permission.can_discharge_vacuum
        && !permission.can_add_vacuum && !permission.can_remove_vacuum
      ) {
        alert('User has no any permission!')
      }
    })

    this.loginRequest.email = ''
    this.loginRequest.password = ''
  }
}
