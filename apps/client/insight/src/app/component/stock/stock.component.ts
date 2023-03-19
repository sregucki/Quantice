import {Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";
import {Router} from "@angular/router";
import {ArticleService} from "../../service/article-service/article.service";
import {Subscription} from "rxjs";
import {Article} from "../../model/article";
import {StockChart} from "angular-highcharts";
import {StockChartService} from "../../service/stock-chart-service/stock-chart.service";
import {StockService} from "../../service/stock-service/stock.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Notyf} from "notyf";

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
  lastPrice: number;
  lastPriceChange: number;
  lastPriceChangePercent: number;
  articleSearchForm: FormGroup;

  constructor(private location: Location, private router: Router, private articleService: ArticleService,
              private stockChartService: StockChartService, private stockService: StockService,
              fb: FormBuilder) {

    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras.state as {
      stockName: string,
      ticker: string
    };
    this.ticker = state.ticker;
    this.stockName = state.stockName;
    this.articleSearchForm = fb.group({
      articleSearchKeywords: [null, [
        Validators.required,
        Validators.pattern(/[\S]/g),
      ]],
      articleSearchDateFrom: [null],
      articleSearchDateTo: [null],
    }, {
      updateOn: 'submit'
    });

  }

  ngOnInit(): void {

    this.articleService.getArticlesRss([this.stockName], null, null).subscribe(data => {
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

  onSubmit() {
    if (!this.articleSearchForm.valid) {
      return;
    }
    const notyf = new Notyf({
      duration: 4000,
      position: {
        x: 'right',
        y: 'bottom'
      },
      types: []
    });

    const keywords = this.articleSearchForm.value['articleSearchKeywords'];
    const articleSearchDateFrom = this.articleSearchForm.value['articleSearchDateFrom'];
    const articleSearchDateTo = this.articleSearchForm.value['articleSearchDateTo'];
    if (keywords.length > 0){
      this.articleService.getArticlesRss(keywords.split(' '), articleSearchDateFrom, articleSearchDateTo).subscribe(data => {
        this.initArticles = data;
        if (data.length > 0) {
          this.headers = Object.keys(this.initArticles[0]).slice(1);
        }
        else {
          notyf.open({
            type: 'error',
            message: 'Articles not found!\nPlease refine your search criteria to narrow the results.'
          })
        }
      });
    }
    // (document.getElementById('articleSearchKeywords') as HTMLInputElement).value = '';
    // (document.getElementById('articleSearchDateFrom') as HTMLInputElement).value = '';
    // (document.getElementById('articleSearchDateTo') as HTMLInputElement).value = '';
  }


}
