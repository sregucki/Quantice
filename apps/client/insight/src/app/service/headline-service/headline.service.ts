import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

export interface Headline {
  url: string;
  title: string;
  imageUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class HeadlineService  {

  constructor(private http: HttpClient) { }

  getHeadlines(): Observable<any> {
    return this.http.get<object>('http://localhost:8083/headlines');
  }

}
