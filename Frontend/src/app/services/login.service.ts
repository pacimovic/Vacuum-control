import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';
import { LoginRequest, LoginResponse } from '../model';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private readonly apiUrl = environment.loginApi

  constructor(private htttpClient: HttpClient) { }


  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.htttpClient.post<LoginResponse>(this.apiUrl, loginRequest).
    pipe(catchError(this.handleError));
  }


  private handleError(error: HttpErrorResponse){
      if (error.status === 0) {
        // A client-side or network error occurred. Handle it accordingly.
        console.error('An error occurred:', error.error);
      } else if(error.status === 401) {
        alert('Wrong Email or Password!')
      }
      // Return an observable with a user-facing error message.
      return throwError(() => new Error('Something bad happened; please try again later.'));
    }

}
