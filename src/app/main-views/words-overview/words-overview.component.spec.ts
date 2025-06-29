import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WordsOverviewComponent } from './words-overview.component';

describe('WordsOverviewComponent', () => {
  let component: WordsOverviewComponent;
  let fixture: ComponentFixture<WordsOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WordsOverviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WordsOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
