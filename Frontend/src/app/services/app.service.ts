import { Injectable } from '@angular/core';
import { Permission } from '../model';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  permission: Permission = JSON.parse(localStorage.getItem("permission")!)
  
}
