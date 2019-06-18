import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Title } from '../models/title.model';
import { TitleService } from '../services/title.service';


@Component({
  selector: 'app-titles',
  templateUrl: './titles.component.html',
  styleUrls: ['./titles.component.css'],
})


export class TitlesComponent implements OnInit {

  titles: Title[];
  title: Title = new Title();

  constructor(private router: Router, private titleService: TitleService) {

  }

  ngOnInit() {
    this.titleService.getTitles()
    .subscribe( data => {
      this.titles = data;
    });
  }

  createTitle(): void {
    this.titleService.createTitle(this.title.title)
        .subscribe( data => {

          alert('New title was created!');
          });
  }

  deleteTitle(title: Title): void {
    this.titleService.deleteTitles(title)
        .subscribe( data => {
          this.titles = this.titles.filter(t => t !== title);
        });
  }



}
