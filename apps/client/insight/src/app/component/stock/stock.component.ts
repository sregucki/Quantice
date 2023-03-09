import {Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";
import {ActivationEnd, Router} from "@angular/router";
import {ArticleService} from "../../service/article.service";
import {Subscription} from "rxjs";
import {Article} from "../../model/article";

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.css']
})
export class StockComponent implements OnInit {
  sub: Subscription;
  stockName: string;
  headers: string[];
  initArticles: Article[];

  constructor(private location: Location, private router: Router, private articleService: ArticleService) {

    this.sub = router.events.subscribe((val) => {
      if (val instanceof ActivationEnd) {
        if (location.path() != '') {
          this.stockName = location.path().split("/")[2];
        } else {
          this.stockName = 'home' // TODO error here
        }
      }
    });
  }

  ngOnInit(): void {
    this.articleService.getArticlesNewsApi(this.stockName).subscribe(data => {
      this.initArticles = data;
      this.headers = Object.keys(this.initArticles[0]).slice(1);
    });
  }

  redirect(url: string) {
    window.open(url, "_blank");
  }

  getDomainFromUrl(url: string): string {
    const a = document.createElement('a');
    a.setAttribute('href', url);
    return a.hostname.replace('www.', '');
  }

  parseInstant(instant: string) {
    const d = new Date(instant);
    return d.toDateString();
  }

}
