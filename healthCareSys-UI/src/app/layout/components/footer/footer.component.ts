import {Component} from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html'
})
export class FooterComponent {
 menuData = [
              { label: 'Company', url: '/company' },
              { label: 'Careers', url: '/careers' },
              { label: 'Blog', url: '/blog' },
              { label: 'Contact', url: '/contact' },
              { label: 'Privacy Policy', url: '/privacy' },
            ]
}
