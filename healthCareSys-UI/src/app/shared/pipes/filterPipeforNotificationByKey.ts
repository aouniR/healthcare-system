import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter',
  standalone: true, 
})
export class FilterPipeforNotificationByKey implements PipeTransform {
  transform(items: any[], searchTerm: string): any[] {
    if (!items || !searchTerm) {
      return items;
    }
    return items.filter(item => item.key.toLowerCase().includes(searchTerm.toLowerCase()) );
  }
}
