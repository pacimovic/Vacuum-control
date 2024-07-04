import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { ErrorMessage } from '../model';


@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  private readonly apiUrl = environment.errorsApi

  constructor(private httpClient: HttpClient) { }

  getAllErrors(): Observable<ErrorMessage[]> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.get<ErrorMessage[]>(`${this.apiUrl}`, httpOptions).
    pipe(catchError(this.handleError))
  }


  private handleError(error: HttpErrorResponse){
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
