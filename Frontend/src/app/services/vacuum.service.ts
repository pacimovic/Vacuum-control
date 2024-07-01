import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { NewVacuum, Vacuum } from '../model';

@Injectable({
  providedIn: 'root'
})
export class VacuumService {

  private readonly apiUrl = environment.vacuumsApi

  constructor(private httpClient: HttpClient) { }

  getVacuums(name: string, statuses: string[], dateFrom: string, dateTo: string): Observable<Vacuum[]> {
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

  createVacuum(vacuum: NewVacuum): Observable<Vacuum> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.post<Vacuum>(this.apiUrl, vacuum, httpOptions).
    pipe(catchError(this.handleError));
  }

  findVacuum(id: number): Observable<Vacuum> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.get<Vacuum>(`${this.apiUrl}/${id}`, httpOptions).
    pipe(catchError(this.handleError))
  }

  startVacuum(vacuum: Vacuum): Observable<Vacuum> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.put<Vacuum>(`${this.apiUrl}/start/${vacuum.id}`, vacuum, httpOptions).
    pipe(catchError(this.handleError))
  }

  stopVacuum(vacuum: Vacuum): Observable<Vacuum> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.put<Vacuum>(`${this.apiUrl}/stop/${vacuum.id}`, vacuum, httpOptions).
    pipe(catchError(this.handleError))
  }

  dischargeVacuum(vacuum: Vacuum): Observable<Vacuum> {
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      })
    }
    return this.httpClient.put<Vacuum>(`${this.apiUrl}/discharge/${vacuum.id}`, vacuum, httpOptions).
    pipe(catchError(this.handleError))
  }

  private handleError(error: HttpErrorResponse){
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else if(error.status === 403) {
      alert('You dont have permission for this resource!')
    } else if(error.status === 418) {
      alert('Vacuum cleaner with given name already exists!')
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
