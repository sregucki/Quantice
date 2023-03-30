import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class KeywordHighlightService {

  constructor() { }

  highlightKeywords(keywords: string[]) {
    Array.from(document.getElementsByClassName("article-title-span")).forEach(
      (element, index, array) => {
        keywords.forEach((keyword) => {
          const regex = new RegExp(keyword, 'gi');
          element.innerHTML = element.innerHTML.replace(regex, `<span style="background-color: rgba(66, 133, 244, 0.3);">` + element.innerHTML.match(regex) + `</span>`)
        })
      });
  }

}
