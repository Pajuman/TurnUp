import {Component, input, InputSignal} from '@angular/core';
import {WordDTO} from '../api';

@Component({
  selector: 'card',
  imports: [],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
public readonly word: InputSignal<WordDTO> = input.required();
}
