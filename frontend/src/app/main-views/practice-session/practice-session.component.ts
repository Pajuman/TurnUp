import {
  Component,
  inject,
  OnInit,
  signal,
  WritableSignal,
} from '@angular/core';
import { CardComponent } from '../../features/card/card.component';
import { Button } from 'primeng/button';
import { WordDTO } from '../../api';
import { StateService } from '../../services/state.service';
import { Word } from '../../constants-interfaces/interfaces';
import { Router } from '@angular/router';

@Component({
  selector: 'practice-session',
  imports: [CardComponent, Button],
  templateUrl: './practice-session.component.html',
  styleUrl: './practice-session.component.scss',
})
export class PracticeSessionComponent implements OnInit {
  public words: WritableSignal<Word[]> = signal([]);
  public currentCount = signal(0);
  public practiceCount = signal(20);
  public currentWord: WordDTO = {} as WordDTO;
  private separatedWords: WordDTO[][] = [[], [], [], [], [], []];
  private leftBorder = 0;
  private rightBorder = 5;
  private spread = 0;
  private generatedMax = 0;
  private currentWordArrayIndex = 0;
  private currentWordIndex = 0;
  private previousWords: string[] = [];
  private readonly stateService = inject(StateService);
  private readonly router = inject(Router);

  ngOnInit(): void {
    this.words.set(this.stateService.activeLessonWords);
    this.practiceCount.set(this.stateService.practiceCount.value);
    if (this.stateService.languageSwitched) {
      this.switchLanguages();
    }
    this.separateWords();
    this.nextWord();
  }

  public correct() {
    if (this.currentWord.score < 5) {
      ++this.currentWord.score;

      this.separatedWords[this.currentWordArrayIndex + 1].push(
        this.currentWord,
      );
      this.separatedWords[this.currentWordArrayIndex].splice(
        this.currentWordIndex,
        1,
      );
    }
    this.nextWord();
  }

  public wrong() {
    if (this.currentWord.score > 0) {
      --this.currentWord.score;
      this.separatedWords[this.currentWordArrayIndex - 1].push(
        this.currentWord,
      );
      this.separatedWords[this.currentWordArrayIndex].splice(
        this.currentWordIndex,
        1,
      );
    }
    this.nextWord();
  }

  public back() {
    this.router.navigate(['lesson', this.stateService.activeLesson.lessonName]);
  }

  private nextWord() {
    this.setBothBorders();
    this.spread = this.rightBorder - this.leftBorder + 1;
    this.setNewCurrentWord();
    this.currentCount.update((value) => value + 1);
    if (this.currentCount() === this.practiceCount()) {
      this.back();
    }
  }

  private separateWords() {
    this.words().forEach((word) => {
      this.separatedWords[word.score].push(word);
    });
  }

  private setBothBorders() {
    this.leftBorder = 0;
    this.setBorder(0, 'plus');
    this.rightBorder = 5;
    this.setBorder(5, 'minus');
  }

  private setBorder(index: number, operator: 'plus' | 'minus') {
    if (this.separatedWords[index].length === 0) {
      if (operator === 'plus') {
        this.setBorder(++this.leftBorder, operator);
      } else {
        this.setBorder(--this.rightBorder, operator);
      }
    } else {
      return;
    }
  }

  private getRandomInt(arrLength: number, min = 0) {
    return Math.floor(Math.random() * (arrLength - min)) + min;
  }

  private setNewCurrentWord() {
    this.currentWord = this.generateSemiRandomWord();
    if (this.previousWords.includes(this.currentWord.id)) {
      this.currentWord = this.generateSemiRandomWord();
    } else {
      this.previousWords.push(this.currentWord.id);
    }
    if (this.previousWords.length > 2) {
      this.previousWords.shift();
    }
  }

  private generateSemiRandomWord() {
    this.generatedMax = -1;
    for (let i = this.spread; i > 0; i--) {
      const arrIndex = this.leftBorder + this.spread - i;
      this.generatedMax += this.separatedWords[arrIndex].length * i;
    }
    let generatedNumber = this.getRandomInt(this.generatedMax);
    for (let i = this.spread; i > 0; i--) {
      this.currentWordArrayIndex = this.leftBorder + this.spread - i;
      const numsInArr =
        this.separatedWords[this.currentWordArrayIndex].length * i;
      if (generatedNumber < numsInArr) {
        this.currentWordIndex =
          generatedNumber %
          this.separatedWords[this.currentWordArrayIndex].length;
        return this.separatedWords[this.currentWordArrayIndex][
          this.currentWordIndex
        ];
      }
      generatedNumber -= numsInArr;
    }
    return {} as WordDTO;
  }

  private switchLanguages() {
    this.words().forEach((word) => {
      [word.question, word.answer] = [word.answer, word.question];
    });
  }
}
