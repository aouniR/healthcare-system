import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-two-column-with-image',
  templateUrl: './two-column-with-image.component.html'
})
export class TwoColumnWithImageComponent {
  @Input() fields: any;

  onMenuItemClick() {
    const element = document.getElementById("features-section");
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
      element.focus(); 
    }
  }
}
