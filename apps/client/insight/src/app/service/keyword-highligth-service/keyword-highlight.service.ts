import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class KeywordHighlightService {

  constructor() { }

  highlightKeywords(keywords: string[]) {
    console.log(keywords);
    Array.from(document.getElementsByClassName("article-title-span")).forEach(
      (element, index, array) => {
        keywords.forEach((keyword) => {
          const regex = new RegExp(keyword, 'gi');
          element.innerHTML = element.innerHTML.replace(regex, `<span style="text-decoration: underline dodgerblue">` + element.innerHTML.match(regex) + `</span>`)
        })
      });
  }

}
