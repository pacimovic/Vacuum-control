import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Permission } from '../model';

export const permissionGuard: CanActivateFn = (route, state) => {

  const router = inject(Router)

  
  var permission: Permission = JSON.parse(localStorage.getItem("permission")!)

  var routePath: string = route.routeConfig?.path!

  if(routePath === 'createUser' && permission.can_create_users == false){
    window.alert("You dont have permission for this page!")
    router.navigate(['login'])
  }
  else if(routePath === 'showUsers' && permission.can_read_users == false){
    window.alert("You dont have permission for this page!")
    router.navigate(['login'])
  }
  else if(routePath === 'updateUser/:id' && permission.can_update_users == false){
    window.alert("You dont have permission for this page!")
    router.navigate(['login'])
  }


  return true;
};
