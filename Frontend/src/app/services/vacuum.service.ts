import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Vacuum } from '../model';

@Injectable({
  providedIn: 'root'
})
export class VacuumService {

  private readonly apiUrl = environment.vacuumsApi

  constructor(private httpClient: HttpClient) { }

  getVacuums(name: string, statuses: string[], dateFrom: string, dateTo: string): Observable<Vacuum[]> {
    //let statuses: string[] = ['STOPPED', 'RUNNING'];
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      }),
      params: new HttpParams({
        fromObject: {
          'name': name,
          'status': statuses,
          'dateFrom': dateFrom,
          'dateTo': dateTo
        }
      })
    }
    return this.httpClient.get<Vacuum[]>(`${this.apiUrl}/search`, httpOptions).
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
