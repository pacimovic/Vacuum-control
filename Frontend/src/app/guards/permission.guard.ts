import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Permission } from '../model';

export const permissionGuard: CanActivateFn = (route, state) => {

  const router = inject(Router)

  
  var permission: Permission = JSON.parse(localStorage.getItem("permission")!)

  var routePath: string = route.routeConfig?.path!

  if(routePath === 'createUser' && permission.can_create_users == false){
    window.alert("You dont have permission to create user!")
    router.navigate(['login'])
  }
  else if(routePath === 'showUsers' && permission.can_read_users == false){
    window.alert("You dont have permission to read users!")
    router.navigate(['login'])
  }
  else if(routePath === 'updateUser/:id' && permission.can_update_users == false){
    window.alert("You dont have permission to update user!")
    router.navigate(['login'])
  }
  else if(routePath === 'searchVacuums' && permission.can_search_vacuum == false){
    window.alert("You dont have permission to search vacuum cleaners!")
    router.navigate(['login'])
  }
  else if(routePath === 'createVacuum' && permission.can_add_vacuum == false){
    window.alert("You dont have permission to create vacuum cleaner!")
    router.navigate(['login'])
  }
  else if(routePath === 'singleVacuum' && permission.can_search_vacuum == false){
    window.alert("You dont have permission to view vacuum cleaner!")
    router.navigate(['login'])
  }


  return true;
};
