import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  // public props
  @Input() cardTitle="Salempou";
  @Input() customHeader: boolean | undefined;
}
