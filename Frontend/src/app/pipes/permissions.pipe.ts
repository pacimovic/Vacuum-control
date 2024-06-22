import { Pipe, PipeTransform } from '@angular/core';
import { User } from '../model';

@Pipe({
  name: 'permissions'
})
export class PermissionsPipe implements PipeTransform {

  transform(user: User): string {

    let permissions: string = ''

    if(user.permission.can_create_users) permissions = permissions.concat('create, ')
    if(user.permission.can_read_users) permissions = permissions.concat('read, ')
    if(user.permission.can_update_users) permissions = permissions.concat('update, ')
    if(user.permission.can_delete_users) permissions = permissions.concat('delete, ')

    return permissions;
  }

}
