import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  public toggler = false;
  public activeUrl = ''; 
  @Output() menuItemClick = new EventEmitter<string>();
  
  private menuData = [
    { url: 'hero-section', label: 'Home' },
    { url: 'two_column_with_image-section', label: 'About' },
    { url: 'features-section', label: 'Features' },
    { url: 'testimonials-section', label: 'Testimonials' }]


  get menuItems() {
    return this.menuData;
  }

  isActive(url: string): boolean {
    return !!(url && url === this.activeUrl); 
  }

  onTogglerClick() {
    this.toggler = !this.toggler;

  }
  
  get isTogglerActive() {
    return this.toggler;
  }
  
  onMenuItemClick(url: string) {
    this.menuItemClick.emit(url);
    //this.isSticky = true;
  }
}


