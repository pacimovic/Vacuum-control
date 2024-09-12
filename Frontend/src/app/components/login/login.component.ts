import { Component } from '@angular/core';
import { LoginRequest, Permission } from 'src/app/model';
import { AppService } from 'src/app/services/app.service';
import { LoginService } from 'src/app/services/login.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{

  loginRequest: LoginRequest = {
    email: '',
    password: ''
  }

  constructor(private loginService: LoginService, private appService: AppService) { 
  }


  login(): void {
    this.loginService.login(this.loginRequest).subscribe(loginResponse => {

      //extractovanje jwt-a
      const helper = new JwtHelperService()
      const decodedToken = helper.decodeToken(loginResponse.jwt);

      //cuvanje jwt-a u local storage
      localStorage.removeItem('jwt')
      localStorage.setItem('jwt', loginResponse.jwt)

      //cuvanje permisija za ulogovanog user-a u local storage
      localStorage.removeItem('permission')
      localStorage.setItem('permission', JSON.stringify(decodedToken.permission))

      //cuvanje username u local storage
      localStorage.removeItem('username')
      localStorage.setItem('username', decodedToken.username)

      //cuvanje id usera u local storage
      localStorage.setItem('id', decodedToken.id)

      //prikaz ili sakrivanje linka za kreiranje korisnika od zavisnosti od permisije
      this.appService.permission = decodedToken.permission

      //prikaz username-a na nav-baru
      this.appService.username = decodedToken.username

      alert('Login successful')

      var permission: Permission = decodedToken.permission
      if (!permission.can_create_users && !permission.can_read_users && !permission.can_update_users && !permission.can_delete_users
        && !permission.can_search_vacuum && !permission.can_start_vacuum && !permission.can_stop_vacuum && !permission.can_discharge_vacuum
        && !permission.can_add_vacuum && !permission.can_remove_vacuum
      ) {
        alert('User has no any permission!')
      }


      //Kada se uspesno login-ujemo konektujemo se na web socket
      this.loginService.connectSocket()

    })

    this.loginRequest.email = ''
    this.loginRequest.password = ''
  }

}
