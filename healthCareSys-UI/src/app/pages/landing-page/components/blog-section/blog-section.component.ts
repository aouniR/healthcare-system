import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-blog-section',
  templateUrl: './blog-section.component.html'
})
export class BlogSectionComponent {

  @Input() posts:any;

  constructor(private router: Router) {
  }

  onClick(slug: any) {
    this.router.navigate(['blog', slug]);
  }

}
