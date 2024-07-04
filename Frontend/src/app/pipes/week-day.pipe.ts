import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'weekDay'
})
export class WeekDayPipe implements PipeTransform {

  transform(dayOfWeek: number): string {

    if(dayOfWeek === 1) return 'Monday'
    if(dayOfWeek === 2) return 'Tuesday'
    if(dayOfWeek === 3) return 'Wednesday'
    if(dayOfWeek === 4) return 'Thursday'
    if(dayOfWeek === 5) return 'Friday'
    if(dayOfWeek === 6) return 'Saturday'
    if(dayOfWeek === 7) return 'Sunday'

    return '*';
  }

}
