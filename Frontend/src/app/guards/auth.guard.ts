import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {

  const router = inject(Router)

  var jwtToken: string = localStorage.getItem("jwt")!
  if(jwtToken == null){
    window.alert("You need to login to access this page!")
    router.navigate(['login'])
  }


  return true;
};
