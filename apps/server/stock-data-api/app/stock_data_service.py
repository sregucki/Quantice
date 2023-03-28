import pandas as pd
import yfinance as yf


def get_historical_stock_data(ticker: str, period: str, interval: str) -> pd.DataFrame:
    return yf.Ticker(ticker).history(period=period, interval=interval)
