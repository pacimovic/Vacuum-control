import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { User } from '../model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly apiUrl = environment.usersApi


  constructor(private httpClient: HttpClient) { }

  getAllUsers(): Observable<User[]> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.get<User[]>(`${this.apiUrl}/all`, httpOptions)
  }

  createUser(user: User): Observable<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.post<User>(this.apiUrl, user, httpOptions).
    pipe(catchError(this.handleError));
  }

  updateUser(user: User): Observable<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.put<User>(this.apiUrl, user, httpOptions).
    pipe(catchError(this.handleError));
  }

  
  findUser(id: number): Observable<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.get<User>(`${this.apiUrl}/${id}`, httpOptions).
    pipe(catchError(this.handleError));
  }

  deleteUser(id: number):  Observable<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.delete<User>(`${this.apiUrl}/${id}`, httpOptions).
    pipe(catchError(this.handleError))
  }





  private handleError(error: HttpErrorResponse){
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else if(error.status === 403) {
      alert('You dont have permission for this resource!')
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
