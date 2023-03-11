import {Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";
import {Router} from "@angular/router";
import {ArticleService} from "../../service/article-service/article.service";
import {Subscription} from "rxjs";
import {Article} from "../../model/article";
import {StockChart} from "angular-highcharts";
import {StockChartService} from "../../service/stock-chart-service/stock-chart.service";
import {StockService} from "../../service/stock-service/stock.service";

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
  stockChart: StockChart;
  ticker: string;
  // lastPrice: string;
  lastPrice: number;
  lastPriceChange: number;
  lastPriceChangePercent: number;

  constructor(private location: Location, private router: Router, private articleService: ArticleService,
              private stockChartService: StockChartService, private stockService: StockService) {

    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as {
      stockName: string,
      ticker: string
    };
    this.ticker = state.ticker;
    this.stockName = state.stockName;
  }

  ngOnInit(): void {

    this.articleService.getArticlesNewsApi(this.stockName).subscribe(data => {
      this.initArticles = data;
      this.headers = Object.keys(this.initArticles[0]).slice(1);
    });
    this.stockService.getStockData(this.ticker, '3y', '1d').subscribe(stockData => {

      const stockDataClose = stockData['data' as keyof typeof stockData]['Close' as keyof typeof stockData];
      const stockDataCloseFormatted = [];
      for (let [date, price] of Object.entries(stockDataClose)) {
        stockDataCloseFormatted.push([Date.parse(date), price])
      }
      this.stockChart = this.stockChartService.getLineChart(this.ticker, stockDataCloseFormatted)

      this.lastPrice = (
        Number(stockDataCloseFormatted[stockDataCloseFormatted.length - 1][1])
      );

      this.lastPriceChange =
        (
          Number(stockDataCloseFormatted[stockDataCloseFormatted.length - 1][1])
        -
          Number(stockDataCloseFormatted[stockDataCloseFormatted.length - 2][1])
        );

      this.lastPriceChangePercent = ((
        (
          Number(stockDataCloseFormatted[stockDataCloseFormatted.length - 1][1])
          /
          Number(stockDataCloseFormatted[stockDataCloseFormatted.length - 2][1])
        ) - 1
      ) * 100);

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