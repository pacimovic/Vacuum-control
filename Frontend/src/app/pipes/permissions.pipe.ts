import { Pipe, PipeTransform } from '@angular/core';
import { User } from '../model';

@Pipe({
  name: 'permissions'
})
export class PermissionsPipe implements PipeTransform {

  transform(user: User): string {

    let permissions: string = ''

    if(user.permission.can_create_users) permissions = permissions.concat('create user, ')
    if(user.permission.can_read_users) permissions = permissions.concat('read users, ')
    if(user.permission.can_update_users) permissions = permissions.concat('update user, ')
    if(user.permission.can_delete_users) permissions = permissions.concat('delete user, ')
    if(user.permission.can_search_vacuum) permissions = permissions.concat('search vacuums, ')
    if(user.permission.can_start_vacuum) permissions = permissions.concat('start vacuum, ')  
    if(user.permission.can_stop_vacuum) permissions = permissions.concat('stop vacuum, ')
    if(user.permission.can_discharge_vacuum) permissions = permissions.concat('discharge vacuum, ')
    if(user.permission.can_add_vacuum) permissions = permissions.concat('add vacuum, ')  
    if(user.permission.can_remove_vacuum) permissions = permissions.concat('remove vacuum, ')

    return permissions;
  }

}
