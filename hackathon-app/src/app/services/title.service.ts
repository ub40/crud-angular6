import {Injectable} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Title } from '../models/title.model';


const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  @Injectable()
  export class TitleService {

    private url = 'http://localhost:8080/hackathon-0.0.1-SNAPSHOT/api/title/';
    constructor(private http: HttpClient) {}

    getTitles() {
      return this.http.get<Title[]>(this.url);
    }

    deleteTitles(title) {
      return this.http.delete(this.url + title.id);
    }

    createTitle(title) {
      return this.http.post<Title>(this.url, {title : title});
    }

  }
