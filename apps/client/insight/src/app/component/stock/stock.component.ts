import {Component} from '@angular/core';
import {Location} from "@angular/common";
import {ActivationEnd, Router} from "@angular/router";
import {ArticleService} from "../../service/article.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.css']
})
export class StockComponent {
  sub: Subscription;
  stockName: string;

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

}
