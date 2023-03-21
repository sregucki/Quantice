import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";
import {Router} from "@angular/router";
import {ArticleService} from "../../service/article-service/article.service";
import {Subscription} from "rxjs";
import {Article} from "../../model/article";
import {StockChart} from "angular-highcharts";
import {StockChartService} from "../../service/stock-chart-service/stock-chart.service";
import {StockService} from "../../service/stock-service/stock.service";
import {FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {Notyf} from "notyf";
import {
  KeywordHighlightService
} from "../../service/keyword-highligth-service/keyword-highlight.service";

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.css']
})
export class StockComponent implements OnInit, AfterViewInit {
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
  keywords: string[];

  constructor(private location: Location, private router: Router, private articleService: ArticleService, public stockChartService: StockChartService, private stockService: StockService,
              fb: FormBuilder, private keywordHighlightService: KeywordHighlightService) {

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
      articleSearchDateFrom: [null, [Validators.required, this.dateRangeValidator]],
      articleSearchDateTo: [null, [Validators.required, this.dateRangeValidator]],

    },
      {
        updateOn: 'submit'
      });
  }

  ngAfterViewInit() {
    const observer = new MutationObserver(() => {
      this.keywordHighlightService.highlightKeywords(this.keywords);
    });
    observer.observe(document.getElementById('articles-list') as Node, { childList:true })
  }

  ngOnInit(): void {
    this.keywords = [this.stockName];
    this.articleService.getArticlesRss(this.keywords, null, null).subscribe(data => {
      this.initArticles = data;
      this.headers = Object.keys(this.initArticles[0]).slice(1);
    });
    this.stockService.getStockData(this.ticker, '3y', '1d').subscribe(stockData => {

      const stockDataOpen = stockData['data' as keyof typeof stockData]['Open' as keyof typeof stockData];
      const stockDataHigh = stockData['data' as keyof typeof stockData]['High' as keyof typeof stockData];
      const stockDataLow = stockData['data' as keyof typeof stockData]['Low' as keyof typeof stockData];
      const stockDataClose = stockData['data' as keyof typeof stockData]['Close' as keyof typeof stockData];
      const stockDataVolume = stockData['data' as keyof typeof stockData]['Volume' as keyof typeof stockData];
      const stockDataCloseFormatted = [];
      const stockDataAll = [];
      for (let [date, close] of Object.entries(stockDataClose)) {
        stockDataCloseFormatted.push([Date.parse(date), close])
      }
      for (let i = 0; i < Object.values(stockDataOpen).length; i++) {
        stockDataAll.push([Date.parse(Object.keys(stockDataOpen)[i]), Object.values(stockDataOpen)[i], Object.values(stockDataHigh)[i], Object.values(stockDataLow)[i], Object.values(stockDataClose)[i], Object.values(stockDataVolume)[i]]);
      }

      this.stockChart = this.stockChartService.getLineChart(this.ticker, stockDataAll, stockDataCloseFormatted);
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
    const articleSearchDateFrom = new Date(this.articleSearchForm.value['articleSearchDateFrom']);
    const articleSearchDateTo = new Date(this.articleSearchForm.value['articleSearchDateTo']);
    if (keywords.length > 0){
      this.articleService.getArticlesRss(keywords.split(' '), new Date(articleSearchDateFrom), new Date(articleSearchDateTo)).subscribe(data => {
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
    this.keywords = keywords.split(' ');
  }

  private dateRangeValidator: ValidatorFn = (): {
    [key: string]: boolean;
  } | null => {
    let invalid = false;
    const from = this.articleSearchForm && this.articleSearchForm.get("articleSearchDateFrom")!.value;
    const to = this.articleSearchForm && this.articleSearchForm.get("articleSearchDateTo")!.value;
    if (from && to) {
      invalid = new Date(from).valueOf() >= new Date(to).valueOf();
    }
    return invalid ? { invalidRange: true }  : null;
  };

}
