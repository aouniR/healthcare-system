import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter',
  standalone: true, 
})
export class filterPipeforMetaModelsByType implements PipeTransform {
  transform(items: any[], searchTerm: string): any[] {
    if (!items || !searchTerm) {
      return items;
    }
    return items.filter(item => item.type.toLowerCase().includes(searchTerm.toLowerCase()) );
  }
}
