import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ShowUsersComponent } from './components/show-users/show-users.component';
import { authGuard } from './guards/auth.guard';
import { CreateUserComponent } from './components/create-user/create-user.component';
import { permissionGuard } from './guards/permission.guard';
import { UpdateUserComponent } from './components/update-user/update-user.component';
import { SearchVacuumsComponent } from './components/search-vacuums/search-vacuums.component';
import { CreateVacuumComponent } from './components/create-vacuum/create-vacuum.component';
import { SingleVacuumComponent } from './components/single-vacuum/single-vacuum.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'showUsers',
    component: ShowUsersComponent,
    canActivate: [authGuard, permissionGuard]
  },
  {
    path: 'createUser',
    component: CreateUserComponent,
    canActivate: [authGuard, permissionGuard]
  },
  {
    path: 'updateUser/:id',
    component: UpdateUserComponent,
    canActivate: [authGuard, permissionGuard]
  },
  {
    path: 'searchVacuums',
    component: SearchVacuumsComponent,
    canActivate: [authGuard, permissionGuard]
  },
  {
    path: 'createVacuum',
    component: CreateVacuumComponent,
    canActivate: [authGuard, permissionGuard]
  },
  {
    path: 'singleVacuum/:id',
    component: SingleVacuumComponent,
    canActivate: [authGuard, permissionGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
